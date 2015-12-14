package com.guo.duoduo.customadapter;


import java.util.List;

import android.content.Context;

import com.guo.duoduo.customadapterlibrary.CustomAdapter;
import com.guo.duoduo.customadapterlibrary.ViewHolder;


/**
 * Created by 郭攀峰 on 2015/12/14.
 */
public class MyAdapter extends CustomAdapter<Friends>
{
    public MyAdapter(Context context, int resLayoutId, List<Friends> list)
    {
        super(context, resLayoutId, list);
    }

    @Override
    public void convert(ViewHolder viewHolder, Friends friends)
    {
        viewHolder.getTextView(R.id.grid_item).setText(friends.name);
    }
}
