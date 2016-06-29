package com.joker.jokerlibrary.adapter.abslistview;

/**
 * Created by Joker on 2016/6/23.
 */
public interface MultiItemTypeSupport<T>
{
    int getLayoutId(int position,T t);
    int getViewTypeCount();
    int getItemViewType(int position,T t);
}
