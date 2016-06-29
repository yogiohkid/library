package com.joker.jokerlibrary.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joker.jokerlibrary.utils.ImageLoadingUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Joker on 2016/5/12.
 */
public class ViewHolder
{
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    protected ImageLoader imageLoader;
    protected DisplayImageOptions displayImageOptions;
    protected ImageLoadingUtils imageLoadingListener;

    private ViewHolder(Context context, ViewGroup parent,int layoutId,int position,DisplayImageOptions displayImageOptions)
    {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        this.displayImageOptions = displayImageOptions;
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoadingListener = new ImageLoadingUtils();
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);
    }

    public View getConvertView()
    {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if(view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position, DisplayImageOptions displayImageOptions)
    {
        if(convertView == null)
        {
            return new ViewHolder(context,parent,layoutId,position,displayImageOptions);
        }
        return (ViewHolder) convertView.getTag();
    }

    public int getPosition()
    {
        return mPosition;
    }

    /*********************************通用属性设定************************************/
    public ViewHolder setVisibility(int itemId,int visibility)
    {
        View view = getView(itemId);
        view.setVisibility(visibility);
        return this;
    }

    public ViewHolder setOnClickListener(int itemId, View.OnClickListener listener)
    {
        View view = getView(itemId);
        view.setOnClickListener(listener);
        return this;
    }

    /*********************************TextView属性设定************************************/
    public ViewHolder setText(int itemId,String str)
    {
        TextView v = getView(itemId);
        v.setText(str);
        return this;
    }

    public ViewHolder setTextBackground(int itemId,int color)
    {
        TextView v = getView(itemId);
        v.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setTextBackgroundResource(int itemId,int colorId)
    {
        TextView v = getView(itemId);
        v.setBackgroundResource(colorId);
        return this;
    }

    public ViewHolder setTextColor(int itemId,int color)
    {
        TextView v = getView(itemId);
        v.setTextColor(color);
        return this;
    }

    /*********************************ImageView属性设定************************************/
    public ViewHolder setImageUrl(int itemId,String url)
    {
        ImageView v = getView(itemId);
        imageLoader.displayImage(url,v,displayImageOptions,imageLoadingListener);
        return this;
    }
}
