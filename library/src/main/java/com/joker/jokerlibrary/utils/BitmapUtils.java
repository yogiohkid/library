package com.joker.jokerlibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Joker on 2016/6/2.
 */
public class BitmapUtils
{
    private static Bitmap compressImage(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        int options = 90;
        while (baos.toByteArray().length / 1024 > 50)
        {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,baos);
            options -= 10;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap1 = BitmapFactory.decodeStream(bais,null,null);
        return bitmap1;
    }

    private static Bitmap getImage(String srcPath)
    {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh, ww;
        if (w > h)
        {
            hh = 720;
            ww = 1280;
        } else
        {
            hh = 1280;
            ww = 720;
        }
        int be = 1;
        be = (w / ww > h / hh) ? Math.round(w / ww) : Math.round(h / hh);
        if(be <= 0)
        {
            be = 1;
        }
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(srcPath,newOpts);
        return compressImage(bitmap);
    }

    public static Bitmap getImage(File file)
    {
        String path = file.getAbsolutePath();
        return getImage(path);
    }
}
