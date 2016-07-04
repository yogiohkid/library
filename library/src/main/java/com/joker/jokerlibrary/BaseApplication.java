package com.joker.jokerlibrary;

import android.app.Application;
import android.content.Context;

/**
 * Created by Joker on 2016/7/4.
 */
public class BaseApplication extends Application
{
    private static Context mContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext()
    {
        return mContext;
    }
}
