package com.guo.duoduo.customadapter.adapter;


import java.util.List;

import android.content.Context;

import com.guo.duoduo.customadapter.R;
import com.guo.duoduo.customadapter.adapter.customadapter.CustomAdapter;
import com.guo.duoduo.customadapter.adapter.customadapter.ViewHolder;
import com.guo.duoduo.customadapter.entity.Zte;


/**
 * Created by 郭攀峰 on 2015/11/21.
 */
public class MyAdapter extends CustomAdapter<Zte>
{

    public MyAdapter(Context context, int resLayoutId, List<Zte> list)
    {
        super(context, resLayoutId, list);
    }

    @Override
    public void convert(ViewHolder viewHolder, Zte zte)
    {
        viewHolder.getTextView(R.id.id).setText(R.string.app_name + "\t" + zte.mCount);
        viewHolder.getImageView(R.id.project).setBackgroundResource(R.color.colorAccent);
    }
}
