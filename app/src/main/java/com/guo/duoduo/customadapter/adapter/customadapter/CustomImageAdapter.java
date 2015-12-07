package com.guo.duoduo.customadapter.adapter.customadapter;


import java.util.List;

import android.content.Context;
import android.widget.AbsListView;


/**
 * Created by 郭攀峰 on 2015/12/7.
 */
public abstract class CustomImageAdapter<T> extends CustomAdapter<T>
    implements
        AbsListView.OnScrollListener
{
    protected AbsListView mAbsListView;
    private AbsListView.OnScrollListener mOnScrollListener;
    protected boolean mIsScrolling;

    public CustomImageAdapter(AbsListView view, Context context, int resLayoutId,
            List<T> list)
    {
        super(context, resLayoutId, list);
        this.mAbsListView = view;
        this.mAbsListView.setOnScrollListener(this);
    }

    public void addOnScrollListener(AbsListView.OnScrollListener listener)
    {
        this.mOnScrollListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        if (scrollState == SCROLL_STATE_IDLE)
            mIsScrolling = false;
        else
            mIsScrolling = true;
        if (mOnScrollListener != null)
            mOnScrollListener.onScrollStateChanged(view, scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount)
    {
        if (mOnScrollListener != null)
        {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                totalItemCount);
        }
    }
}
