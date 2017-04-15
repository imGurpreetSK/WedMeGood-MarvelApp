package me.gurpreetsk.marvel.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.marvel.BuildConfig;
import me.gurpreetsk.marvel.InitApplication;
import me.gurpreetsk.marvel.R;
import me.gurpreetsk.marvel.activity.SettingsActivity;
import me.gurpreetsk.marvel.adapter.ComicsAdapter;
import me.gurpreetsk.marvel.model.Comic;
import me.gurpreetsk.marvel.model.ComicsTable;
import me.gurpreetsk.marvel.utils.EndlessScrollListener;
import me.gurpreetsk.marvel.utils.Endpoints;
import me.gurpreetsk.marvel.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicsFragment extends Fragment {

    @BindView(R.id.comics_recycler_view)
    RecyclerView comicsRecyclerView;
    @BindView(R.id.comics_refresh_layout)
    SwipeRefreshLayout comicsRefreshLayout;

    SharedPreferences preferences;
    ComicsAdapter comicsAdapter;

    private static final String TAG = ComicsFragment.class.getSimpleName();


    public ComicsFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comics, container, false);
        ButterKnife.bind(this, v);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        comicsAdapter = new ComicsAdapter(getContext(), fetchComicsFromDB());
        comicsRecyclerView.setAdapter(comicsAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        comicsRecyclerView.setLayoutManager(layoutManager);
        comicsRecyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView recyclerView) {
                fetchPaginatedComicsDataFromAPI();
            }
        });
        comicsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                comicsAdapter.swap(fetchComicsFromDB());
            }
        });

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                getContext().startActivity(new Intent(getContext(), SettingsActivity.class));
                break;
        }
        return true;
    }

    private List<Comic> fetchComicsFromDB() {
        Cursor cursor = getContext().getContentResolver().query(ComicsTable.CONTENT_URI, null, null, null, null);
        if (comicsRefreshLayout.isRefreshing())
            comicsRefreshLayout.setRefreshing(false);
        return ComicsTable.getRows(cursor, true);
    }


    private void fetchPaginatedComicsDataFromAPI() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        Uri uri = Uri.parse(Endpoints.getComicsUrl())
                .buildUpon()
                .appendQueryParameter("apikey", BuildConfig.MARVEL_KEY)
                .appendQueryParameter("ts", timestamp)
                .appendQueryParameter("hash", Utils.getMD5(timestamp))
                // if page is 1, get results 20-40; 2 -> 40-60 and so on
                .appendQueryParameter("offset",
                        String.valueOf(preferences.getInt(getString(R.string.COMICS_PAGE_NUMBER), 1) * 20))
                .build();

        JsonObjectRequest request = new JsonObjectRequest(uri.toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response);
                        try {
                            JSONArray array = response.getJSONObject("data").getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                Comic comic = new Comic();
                                comic.setId(array.getJSONObject(i).getString("id"));
                                comic.setTitle(array.getJSONObject(i).getString("title"));
                                comic.setDescription(array.getJSONObject(i).getString("description"));
                                comic.setFormat(array.getJSONObject(i).getString("format"));
                                comic.setIssueNumber(array.getJSONObject(i).getString("issueNumber"));
                                comic.setPageCount(array.getJSONObject(i).getString("pageCount"));
                                comic.setThumbnail(array.getJSONObject(i).getJSONObject("thumbnail")
                                        .getString("path") + ".jpg");
                                comic.setResourceURI(array.getJSONObject(i).getString("resourceURI"));
                                try {
                                    getContext().getContentResolver().insert(ComicsTable.CONTENT_URI,
                                            ComicsTable.getContentValues(comic, true));
                                } catch (Exception e) {
                                    Log.e(TAG, "onResponse: entry already exists");
                                }
                            }
                            preferences.edit()
                                    .putInt(getString(R.string.COMICS_PAGE_NUMBER),
                                            preferences.getInt(getString(R.string.COMICS_PAGE_NUMBER), 0) + 1)
                                    .apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        comicsAdapter.swap(fetchComicsFromDB());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error while fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(15000, 3, 1));
        InitApplication.getInstance().addToQueue(request);
    }

}
