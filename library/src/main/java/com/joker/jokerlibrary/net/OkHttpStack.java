package com.joker.jokerlibrary.net;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joker on 2016/5/17.
 */
public class OkHttpStack extends HurlStack
{
    private final OkUrlFactory okUrlFactory;

    public OkHttpStack()
    {
        this(new OkUrlFactory(new OkHttpClient()));
    }

    public OkHttpStack(OkUrlFactory okUrlFactory)
    {
        if (okUrlFactory == null)
        {
            throw new NullPointerException("kong");
        }
        this.okUrlFactory = okUrlFactory;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException
    {
        return okUrlFactory.open(url);
    }
}
