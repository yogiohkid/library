package com.joker.jokerlibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Joker on 2016/6/21.
 */
public abstract class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    public void navigatorTo(final String activityName, final Intent intent)
    {
        Class<?> clazz = null;
        try
        {
            clazz = Class.forName(activityName);
            if(clazz != null)
            {
                intent.setClass(this,clazz);
                this.startActivity(intent);
            }
        } catch (ClassNotFoundException e)
        {
            return;
        }
    }

    public void navigatorForResult(final String activityName, final Intent intent,final int response)
    {
        Class<?> clazz = null;
        try
        {
            clazz = Class.forName(activityName);
            if(clazz != null)
            {
                intent.setClass(this,clazz);
                this.startActivityForResult(intent,response);
            }
        } catch (ClassNotFoundException e)
        {
            return;
        }
    }

    protected abstract void initVariables();
    protected abstract void initViews(Bundle savedInstanceState);
    protected abstract void loadData();
}
