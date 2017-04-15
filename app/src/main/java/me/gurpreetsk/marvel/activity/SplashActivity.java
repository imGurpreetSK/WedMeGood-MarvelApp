package me.gurpreetsk.marvel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import me.gurpreetsk.marvel.BuildConfig;
import me.gurpreetsk.marvel.InitApplication;
import me.gurpreetsk.marvel.R;
import me.gurpreetsk.marvel.model.Character;
import me.gurpreetsk.marvel.model.CharactersTable;
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
                                    getContentResolver().insert(ComicsTable.CONTENT_URI,
                                            ComicsTable.getContentValues(comic, true));
                                } catch (Exception e) {
                                    Log.e(TAG, "onResponse: entry already exists");
                                }
                            }
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

        Uri charUri = Uri.parse(Endpoints.getCharactersUrl())
                .buildUpon()
                .appendQueryParameter("apikey", BuildConfig.MARVEL_KEY)
                .appendQueryParameter("ts", timestamp)
                .appendQueryParameter("hash", Utils.getMD5(timestamp))
                .build();

        JsonObjectRequest charRequest = new JsonObjectRequest(charUri.toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response);
                        try {
                            JSONArray array = response.getJSONObject("data").getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                Character character = new Character();
                                character.setId(array.getJSONObject(i).getString("id"));
                                character.setName(array.getJSONObject(i).getString("name"));
                                character.setDescription(array.getJSONObject(i).getString("description"));
                                character.setThumbnail(array.getJSONObject(i).getJSONObject("thumbnail")
                                        .getString("path") + ".jpg");
                                try {
                                    getContentResolver().insert(CharactersTable.CONTENT_URI,
                                            CharactersTable.getContentValues(character, true));
                                } catch (Exception e) {
                                    Log.e(TAG, "onResponse: entry already exists");
                                }
                            }
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
        charRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 3, 1));
        InitApplication.getInstance().addToQueue(charRequest);

    }


    @Override
    protected void onStop() {
        super.onStop();
        preferences.edit().putBoolean(getString(R.string.IS_FIRST_RUN), false)
                .putInt(getString(R.string.COMICS_PAGE_NUMBER), 1)
                .putInt(getString(R.string.CHARACTERS_PAGE_NUMBER), 1)
                .apply();
        finish();
    }

}
