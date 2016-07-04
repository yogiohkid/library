package com.joker.jokerlibrary.activity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.joker.jokerlibrary.R;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by Joker on 2016/6/30.
 */
public abstract class QRScanActivity extends BaseActivity implements SurfaceHolder.Callback
{
    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener()
    {
        public void onCompletion(MediaPlayer mediaPlayer)
        {
            mediaPlayer.seekTo(0);
        }
    };

    protected CaptureActivityHandler handler;
    protected ViewfinderView viewfinderView;
    protected boolean hasSurface;
    protected Vector<BarcodeFormat> decodeFormats;
    protected String characterSet;
    protected InactivityTimer inactivityTimer;
    protected MediaPlayer mediaPlayer;
    protected boolean playBeep;
    protected boolean vibrate;

    protected int PreViewViewId;

    @Override
    protected void onResume()
    {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(PreViewViewId);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface)
        {
            initCamera(surfaceHolder);
        } else
        {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
        {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (handler != null)
        {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy()
    {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        if (!hasSurface)
        {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        hasSurface = false;
    }

    public void drawViewfinder()
    {
        viewfinderView.drawViewfinder();
    }

    public Handler getHandler()
    {
        return handler;
    }

    public ViewfinderView getViewfinderView()
    {
        return viewfinderView;
    }

    protected void initCamera(SurfaceHolder surfaceHolder)
    {
        try
        {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException e)
        {
            return;
        } catch (RuntimeException e1)
        {
            return;
        }
        if(handler == null)
        {
            handler = new CaptureActivityHandler(this,decodeFormats,characterSet);
        }
    }

    protected void initBeepSound()
    {
        if(playBeep && mediaPlayer == null)
        {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try
            {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e)
            {
                mediaPlayer = null;
            }
        }
    }

    protected void playBeepSoundAndVibrate()
    {
        if (playBeep && mediaPlayer != null)
        {
            mediaPlayer.start();
        }
        if (vibrate)
        {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    public abstract void handleDecode(Result result, Bitmap barcode);
}
