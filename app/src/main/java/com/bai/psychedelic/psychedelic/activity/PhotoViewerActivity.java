package com.bai.psychedelic.psychedelic.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bai.psychedelic.psychedelic.R;
import com.bai.psychedelic.psychedelic.adapter.PhotoViewPagerAdapter;
import com.bai.psychedelic.psychedelic.util.PsyLog;

import java.util.ArrayList;

public class PhotoViewerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private Context mContext;
    private ArrayList<String> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        mContext = this;
        Intent mIntent = getIntent();
        mList = mIntent.getStringArrayListExtra("photo_url_list");
        PsyLog.d("yiqing","photo size = "+mList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager = findViewById(R.id.photo_viewer_pager);
        mViewPager.setAdapter(new PhotoViewPagerAdapter(mContext,mList));

    }
}
