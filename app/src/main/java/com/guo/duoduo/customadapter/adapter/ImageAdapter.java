package com.guo.duoduo.customadapter.adapter;


import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.guo.duoduo.customadapter.R;
import com.guo.duoduo.customadapter.entity.GridItem;
import com.guo.duoduo.customadapter.utils.NativeImageLoader;
import com.guo.duoduo.customadapter.view.MyImageView;
import com.guo.duoduo.customadapterlibrary.CustomImageAdapter;
import com.guo.duoduo.customadapterlibrary.ViewHolder;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleAdapter;


/**
 * Created by 郭攀峰 on 2015/12/7.
 */
public class ImageAdapter extends CustomImageAdapter<GridItem>
    implements
        AbsListView.OnScrollListener,
        StickyGridHeadersSimpleAdapter
{
    private boolean mIsInit = false;
    private StickyGridHeadersGridView mStickyGridHeadersGridView;
    private Point mPoint = new Point(0, 0);
    private int mStartIndex, mEndIndex;

    public ImageAdapter(AbsListView view, Context context, int resLayoutId,
            List<GridItem> list)
    {
        super(view, context, resLayoutId, list);
        this.mStickyGridHeadersGridView = (StickyGridHeadersGridView) view;
        this.mStickyGridHeadersGridView.setOnScrollListener(this);
    }

    @Override
    public void convert(ViewHolder viewHolder, GridItem gridItem)
    {
        MyImageView mImageView = (MyImageView) viewHolder.getImageView(R.id.grid_item);
        mImageView.setImageResource(R.mipmap.friends_sends_pictures_no);

        mImageView.setOnMeasureListener(new MyImageView.OnMeasureListener()
        {
            @Override
            public void onMeasureSize(int width, int height)
            {
                mPoint.set(width, height);
            }
        });

        String path = gridItem.getPath();
        mImageView.setTag(path);

        if (!mIsInit)
        {
            Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint,
                new NativeImageLoader.NativeImageCallBack()
                {

                    @Override
                    public void onImageLoader(Bitmap bitmap, String path)
                    {
                        ImageView mImageView = (ImageView) mStickyGridHeadersGridView
                                .findViewWithTag(path);
                        if (bitmap != null && mImageView != null)
                        {
                            mImageView.setImageBitmap(bitmap);
                        }
                    }
                });

            if (bitmap != null)
                mImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        mIsInit = true;
        switch (scrollState)
        {
            case SCROLL_STATE_IDLE :
                for (; mStartIndex < mEndIndex; mStartIndex++)
                {
                    int realIndex = mStickyGridHeadersGridView
                            .translatePosition(mStartIndex);
                    if (realIndex < 0)
                        continue;

                    String path = mList.get(realIndex).getPath();
                    ImageView img = (ImageView) mStickyGridHeadersGridView
                            .findViewWithTag(path);
                    Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path,
                        mPoint, new NativeImageLoader.NativeImageCallBack()
                        {

                            @Override
                            public void onImageLoader(Bitmap bitmap, String path)
                            {
                                ImageView mImageView = (ImageView) mStickyGridHeadersGridView
                                        .findViewWithTag(path);
                                if (bitmap != null && mImageView != null)
                                {
                                    mImageView.setImageBitmap(bitmap);
                                }
                            }
                        });

                    if (bitmap != null)
                        img.setImageBitmap(bitmap);
                    else
                        img.setImageResource(R.mipmap.friends_sends_pictures_no);
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount)
    {
        mStartIndex = firstVisibleItem;
        mEndIndex = firstVisibleItem + visibleItemCount;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent)
    {
        HeaderViewHolder mHeaderHolder;
        if (convertView == null)
        {
            mHeaderHolder = new HeaderViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.header, parent, false);
            mHeaderHolder.mTextView = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(mHeaderHolder);
        }
        else
        {
            mHeaderHolder = (HeaderViewHolder) convertView.getTag();
        }

        mHeaderHolder.mTextView.setText(mList.get(position).getTime());

        return convertView;
    }

    public static class HeaderViewHolder
    {
        public TextView mTextView;
    }

    @Override
    public long getHeaderId(int position)
    {
        return mList.get(position).getSection();
    }
}