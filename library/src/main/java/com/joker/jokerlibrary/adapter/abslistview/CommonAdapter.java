package com.joker.jokerlibrary.adapter.abslistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.joker.jokerlibrary.adapter.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by Joker on 2016/5/12.
 */
public abstract class CommonAdapter<T> extends BaseAdapter
{
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;
    protected DisplayImageOptions displayImageOptions;

    public CommonAdapter(Context context,List<T> mDatas,int itemLayoutId)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;

        this.displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
//                .showImageOnLoading(android.support.v7.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)
//                .showImageOnFail(R.drawable.default_image)// TODO: 2016/6/21 有可能的话替换为正在加载的一张标识图和加载失败的标示图
                .resetViewBeforeLoading()
                .build();
    }

    public CommonAdapter(Context context,List<T> mDatas,int itemLayoutId,DisplayImageOptions displayImageOptions)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;

        this.displayImageOptions = displayImageOptions;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder = getViewHolder(position,convertView,parent);
        convert(viewHolder,getItem(position),position);
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder holder,T item,int position);

    private ViewHolder getViewHolder(int position,View convertView,ViewGroup parent)
    {
        return ViewHolder.get(mContext,convertView,parent,mItemLayoutId,position,displayImageOptions);
    }
}
