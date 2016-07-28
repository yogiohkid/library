package com.joker.jsbridgedemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.WebView;

/**
 * Created by yogio on 2016/7/27.
 */
public class MyJsScope
{
    public static void alert(WebView webView,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(webView.getContext());
        builder.setTitle("测试消息");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }
}
