package com.guo.duoduo.customadapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;


public class MainActivity extends AppCompatActivity
{
    private static final String tag = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Friends> list = new ArrayList<>();
        for (int i = 0; i < 30; i++)
        {
            Friends friends = new Friends();
            friends.name = "PandarGuo";
            list.add(friends);
        }
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new MyAdapter(this, R.layout.grid_item, list));
    }
}