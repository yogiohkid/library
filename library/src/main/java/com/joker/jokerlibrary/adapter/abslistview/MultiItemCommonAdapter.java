package com.joker.jokerlibrary.adapter.abslistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.joker.jokerlibrary.adapter.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by Joker on 2016/6/23.
 */
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T>
{
    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas,MultiItemTypeSupport<T> multiItemTypeSupport)
    {
        super(context,datas,-1);
        mMultiItemTypeSupport = multiItemTypeSupport;
        if(mMultiItemTypeSupport == null)
        {
            throw new IllegalArgumentException("MultiItemTypeSupport对象不能为空(null)");
        }
        displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
//                .showImageOnLoading(android.support.v7.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)
//                .showImageOnFail(R.drawable.default_image)// TODO: 2016/6/21 有可能的话替换为正在加载的一张标识图和加载失败的标示图
                .resetViewBeforeLoading(true)
                .build();
    }

    public MultiItemCommonAdapter(Context context,List<T> datas,MultiItemTypeSupport<T> multiItemTypeSupport,DisplayImageOptions displayImageOptions)
    {
        super(context,datas,-1);
        mMultiItemTypeSupport = multiItemTypeSupport;
        if(mMultiItemTypeSupport == null)
        {
            throw new IllegalArgumentException("MultiItemTypeSupport对象不能为空(null)");
        }
        super.displayImageOptions = displayImageOptions;
    }

    @Override
    public int getViewTypeCount()
    {
        if(mMultiItemTypeSupport != null)
        {
            return mMultiItemTypeSupport.getViewTypeCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position)
    {
        if(mMultiItemTypeSupport != null)
        {
            return mMultiItemTypeSupport.getItemViewType(position,mDatas.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(mMultiItemTypeSupport == null)
        {
            return super.getView(position,convertView,parent);
        }
        int layoutId = mMultiItemTypeSupport.getLayoutId(position,getItem(position));
        ViewHolder viewHolder = ViewHolder.get(mContext,convertView,parent,layoutId,position,displayImageOptions);
        convert(viewHolder,getItem(position),position);
        return viewHolder.getConvertView();
    }
}
