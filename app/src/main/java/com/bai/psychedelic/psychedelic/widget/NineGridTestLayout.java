package com.bai.psychedelic.psychedelic.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.bai.psychedelic.psychedelic.activity.PhotoViewerActivity;
import com.bai.psychedelic.psychedelic.util.PsyLog;
import com.bai.psychedelic.psychedelic.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Administrator on 2019/1/7.
 */

public class NineGridTestLayout extends NineGridLayout {

    private Context mContext;
    private Picasso mPicasso;
    protected static final int MAX_W_H_RATIO = 3;

    public NineGridTestLayout(Context context) {
        super(context);
        mContext = context;
        if (mPicasso == null){
            mPicasso = new Picasso.Builder(mContext).build();
        }

    }

    public NineGridTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (mPicasso == null){
            mPicasso = new Picasso.Builder(mContext).build();
        }

    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {
        mPicasso.load(url).into(imageView);

        return false;// true 代表按照九宫格默认大小显示(此时不要调用setOneImageLayoutParams)；false 代表按照自定义宽高显示。
    }
    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        mPicasso.load(url).into(imageView);
    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList) {
        PsyLog.d("yiqing","size = "+urlList.size());
        Intent mIntent = new Intent(mContext, PhotoViewerActivity.class);
        mIntent.putStringArrayListExtra("photo_url_list",(ArrayList<String>)urlList);
        mContext.startActivity(mIntent);
    }

}
