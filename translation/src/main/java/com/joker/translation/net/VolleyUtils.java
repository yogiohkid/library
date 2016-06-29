package com.joker.translation.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.joker.jokerlibrary.net.StringVolleyLis;
import com.joker.translation.MyApplication;

import java.util.Map;

/**
 * Created by Joker on 2016/6/21.
 */
public class VolleyUtils
{
    public static StringRequest stringRequest;

    public static void RequestPost(String tag, String url, final Map<String,String> params, StringVolleyLis stringVolleyLis)
    {
        MyApplication.getVolleyQueue().cancelAll(tag);
        stringRequest = new StringRequest(Request.Method.POST,url, stringVolleyLis.responseListener(), stringVolleyLis.errorListener())
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                return super.getParams();
            }
        };
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,1,1.0f));
        MyApplication.getVolleyQueue().add(stringRequest);
    }

    public static void RequestGet(String tag,String url,StringVolleyLis stringVolleyLis)
    {
        MyApplication.getVolleyQueue().cancelAll(tag);
        stringRequest = new StringRequest(Request.Method.GET,url, stringVolleyLis.responseListener(), stringVolleyLis.errorListener());
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 *1000,1,1.0f));
        MyApplication.getVolleyQueue().add(stringRequest);
    }

    public static void RequestGet(String tag,String url,Map<String,Object> params,StringVolleyLis stringVolleyLis)
    {
        MyApplication.getVolleyQueue().cancelAll(tag);

        String requestUrl = url+"?";

        for(String s : params.keySet())
        {
            requestUrl += s +"="+params.get(s)+"&";
        }
        requestUrl = requestUrl.substring(0,requestUrl.length()-1);
        Log.d("VolleyUtils", requestUrl);

        stringRequest = new StringRequest(Request.Method.GET,requestUrl, stringVolleyLis.responseListener(), stringVolleyLis.errorListener());
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 *1000,1,1.0f));
        MyApplication.getVolleyQueue().add(stringRequest);
    }
}
