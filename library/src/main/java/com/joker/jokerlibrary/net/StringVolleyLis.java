package com.joker.jokerlibrary.net;

import android.content.Context;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by Joker on 2016/4/20.
 */
public abstract class StringVolleyLis
{
    public static Response.Listener<String> mListener;
    public static Response.ErrorListener mErrorListener;
    public Context context;

    public StringVolleyLis(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        this.context = context;
        this.mErrorListener = errorListener;
        this.mListener = listener;
    }

    public abstract void onMySuccess(String result);

    public abstract void onMyError(VolleyError error,String description);

    public Response.Listener<String> responseListener()
    {
        mListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String s)
            {
                onMySuccess(s);
            }
        };
        return mListener;
    }

    public Response.ErrorListener errorListener()
    {
        mErrorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

                String description = "";
                if(volleyError instanceof NetworkError)
                {
                    description = "网络错误";
                    if(volleyError.networkResponse != null)
                    {
                        description += (",错误代码："+volleyError.networkResponse.statusCode);
                    }
                }
                else if(volleyError instanceof NoConnectionError)
                {
                    description = "网络连接失败";
                    volleyError.printStackTrace();
                }
                else if(volleyError instanceof ParseError)
                {
                    description = "无法解析服务器返回值";
                    volleyError.printStackTrace();
                }
                else if(volleyError instanceof ServerError)
                {
                    description = "服务器错误";
                    if(volleyError.networkResponse != null)
                    {
                        description += (",错误代码："+volleyError.networkResponse.statusCode);
                    }
                }
                else if(volleyError instanceof TimeoutError)
                {
                    description = "服务器连接超时";
                }
                else
                {
                    description = "未知的网络错误";
                }

                onMyError(volleyError,description);
            }
        };
        return mErrorListener;
    }
}
