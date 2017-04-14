package me.gurpreetsk.marvel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.haha.perflib.Main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import me.gurpreetsk.marvel.BuildConfig;
import me.gurpreetsk.marvel.InitApplication;
import me.gurpreetsk.marvel.R;
import me.gurpreetsk.marvel.model.Comic;
import me.gurpreetsk.marvel.model.ComicsTable;
import me.gurpreetsk.marvel.utils.Endpoints;
import me.gurpreetsk.marvel.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        if (preferences.getBoolean(getString(R.string.IS_FIRST_RUN), true)) //fetch data if first run
            fetchData();
        else
            startActivity(new Intent(SplashActivity.this, MainActivity.class));

    }


    private void fetchData() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        Uri uri = Uri.parse(Endpoints.getComicsUrl())
                .buildUpon()
                .appendQueryParameter("apikey", BuildConfig.MARVEL_KEY)
                .appendQueryParameter("ts", timestamp)
                .appendQueryParameter("hash", Utils.getMD5(timestamp))
                .build();

        JsonObjectRequest request = new JsonObjectRequest(uri.toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONObject("data").getJSONArray("results");
                            Log.i(TAG, "onResponse: " + array.toString());
                            for (int i = 0; i < array.length(); i++) {
                                Comic comic = new Comic();
                                comic.setId(array.getJSONObject(i).getString("id"));
                                comic.setTitle(array.getJSONObject(i).getString("title"));
                                comic.setFormat(array.getJSONObject(i).getString("format"));
                                comic.setIssueNumber(array.getJSONObject(i).getString("issueNumber"));
                                comic.setPageCount(array.getJSONObject(i).getString("pageCount"));
                                comic.setThumbnail(array.getJSONObject(i).getJSONObject("thumbnail")
                                        .getString("path") + ".jpg");
                                comic.setResourceURI(array.getJSONObject(i).getString("resourceURI"));
                                try {
                                    getContentResolver().insert(ComicsTable.CONTENT_URI,
                                            ComicsTable.getContentValues(comic, true));
                                } catch (Exception e) {
                                    Log.e(TAG, "onResponse: entry already exists");
                                }
                            }
                            preferences.edit().putBoolean(getString(R.string.IS_FIRST_RUN), false).apply();
                            preferences.edit().putInt(getString(R.string.PAGE_NUMBER), 1).apply();
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SplashActivity.this, "Error while fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(15000, 3, 1));
        InitApplication.getInstance().addToQueue(request);
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}
