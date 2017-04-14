package me.gurpreetsk.marvel;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by gurpreet on 14/04/17.
 */

public class InitApplication extends Application {

    private RequestQueue queue;
    private static InitApplication sInstance;

    private static final String TAG = InitApplication.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        sInstance = this;
        Stetho.initializeWithDefaults(this);

    }

    public static synchronized InitApplication getInstance() {
        return sInstance;
    }

    public <T> void addToQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getQueue().add(req);
    }

    public <T> void addToQueue(Request<T> req) {
        req.setTag(TAG);
        getQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (queue != null) {
            queue.cancelAll(tag);
        }
    }

    public RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(getApplicationContext());
            return queue;
        }
        return queue;
    }

}
