package me.gurpreetsk.marvel;

import android.net.Uri;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import org.json.JSONObject;

import me.gurpreetsk.marvel.model.Comic;
import me.gurpreetsk.marvel.utils.Endpoints;
import me.gurpreetsk.marvel.utils.Utils;

/**
 * Created by gurpreet on 14/04/17.
 */

public class FetchDataJobService extends JobService {


    @Override
    public boolean onStartJob(JobParameters job) {

//        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
//        Uri uri = Uri.parse(Endpoints.getComicsUrl())
//                .buildUpon()
//                .appendQueryParameter("apikey", BuildConfig.MARVEL_KEY)
//                .appendQueryParameter("ts", timestamp)
//                .appendQueryParameter("hash", Utils.getMD5(timestamp))
//                .build();
//
//        JsonObjectRequest request = new JsonObjectRequest(uri.toString(),
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Comic comic = new Comic()
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//        InitApplication.getInstance().addToQueue(request);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
