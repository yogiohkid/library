package com.joker.qrscanerdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;
import com.joker.jokerlibrary.*;
import com.joker.jokerlibrary.activity.QRScanActivity;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;

public class ScanerActivity extends QRScanActivity
{

    @Override
    protected void initVariables()
    {
        CameraManager.init(getApplicationContext());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        PreViewViewId = R.id.joker_qr_preview_view;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_scaner);
        viewfinderView = (ViewfinderView) findViewById(R.id.joker_qr_viewfinder);
    }

    @Override
    protected void loadData()
    {

    }

    @Override
    public void handleDecode(Result result, Bitmap barcode)
    {
        if (com.joker.jokerlibrary.BuildConfig.DEBUG) Log.d("ScanerActivity", "result:" + result);
    }
}
