package com.guo.duoduo.customadapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.guo.duoduo.customadapter.adapter.ImageAdapter;
import com.guo.duoduo.customadapter.entity.GridItem;
import com.guo.duoduo.customadapter.utils.ImageScanner;
import com.guo.duoduo.customadapter.utils.YMComparator;


public class MainActivity extends AppCompatActivity
{

    private ProgressDialog mProgressDialog;
    private ImageScanner mScanner;
    private GridView mGridView;
    private List<GridItem> mGirdList = new ArrayList<GridItem>();
    private static int section = 1;
    private Map<String, Integer> sectionMap = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGridView = (GridView) findViewById(R.id.asset_grid);
        mScanner = new ImageScanner(this);

        mScanner.scanImages(new ImageScanner.ScanCompleteCallBack()
        {
            {
                mProgressDialog = ProgressDialog.show(MainActivity.this, null, "正在加载...");
            }

            @Override
            public void scanComplete(Cursor cursor)
            {
                // 关闭进度条
                mProgressDialog.dismiss();

                while (cursor.moveToNext())
                {
                    // 获取图片的路径
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    long times = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATE_ADDED));

                    GridItem mGridItem = new GridItem(path, paserTimeToYM(times));
                    mGirdList.add(mGridItem);

                }
                cursor.close();
                Collections.sort(mGirdList, new YMComparator());

                for (ListIterator<GridItem> it = mGirdList.listIterator(); it.hasNext();)
                {
                    GridItem mGridItem = it.next();
                    String ym = mGridItem.getTime();
                    if (!sectionMap.containsKey(ym))
                    {
                        mGridItem.setSection(section);
                        sectionMap.put(ym, section);
                        section++;
                    }
                    else
                    {
                        mGridItem.setSection(sectionMap.get(ym));
                    }
                }

                //mGridView.setAdapter(new StickyGridAdapter(MainActivity.this, mGirdList,
                //    mGridView));

                ImageAdapter imageAdapter = new ImageAdapter(mGridView,
                    MainActivity.this, R.layout.grid_item, mGirdList);
                mGridView.setAdapter(imageAdapter);
            }
        });

    }

    public static String paserTimeToYM(long time)
    {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        return format.format(new Date(time * 1000L));
    }

}
