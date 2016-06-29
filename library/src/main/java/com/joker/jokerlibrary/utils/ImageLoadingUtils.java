package com.joker.jokerlibrary.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joker on 2016/4/21.
 */
public class ImageLoadingUtils extends SimpleImageLoadingListener
{
    public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
    {
        if (loadedImage != null)
        {
            ImageView imageView = (ImageView) view;
            boolean isFirstDisplay = !displayedImages.contains(imageUri);
            if (isFirstDisplay)
            {
                FadeInBitmapDisplayer.animate(imageView, 500);
                displayedImages.add(imageUri);
            }
        }
    }
}
