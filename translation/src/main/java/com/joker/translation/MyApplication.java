package com.joker.translation;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.joker.jokerlibrary.net.OkHttpStack;

/**
 * Created by Joker on 2016/6/21.
 */
public class MyApplication extends Application
{
    private static RequestQueue volleyQueue;

    @Override
    public void onCreate()
    {
        super.onCreate();
        volleyQueue = Volley.newRequestQueue(getApplicationContext(),new OkHttpStack());
        volleyQueue.start();
    }

    public static RequestQueue getVolleyQueue()
    {
        return volleyQueue;
    }
}
