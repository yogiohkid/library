package com.joker.jsbridgedemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import cn.pedant.SafeWebViewBridge.InjectedChromeClient;

/**
 * Created by yogio on 2016/7/26.
 */
public class WebActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TextView tv = new TextView(this);
//        setContentView(tv);
//        tv.setText("Android 5.0及以上，英文字体为Droid Sans字体，中文字体为思源黑体");
//        tv.setTextSize(30);
//        tv.setBackgroundColor(Color.WHITE);
//        tv.setTextColor(Color.BLACK);
//        tv.setPadding(30,100,30,0);

        WebView wv = new WebView(this);
        setContentView(wv);
        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        wv.setWebChromeClient(
//                new CustomChromeClient("HostApp", HostJsScope.class)
                new CustomChromeClient("TestJs",MyJsScope.class)
        );
        wv.loadUrl("file:///android_asset/test2.html");
    }

    public class CustomChromeClient extends InjectedChromeClient
    {

        public CustomChromeClient (String injectedName, Class injectedCls) {
            super(injectedName, injectedCls);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            // to do your work
            // ...
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onProgressChanged (WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // to do your work
            // ...
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            // to do your work
            // ...
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }
}
