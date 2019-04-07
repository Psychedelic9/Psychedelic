package com.bai.psychedelic.psychedelic.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bai.psychedelic.psychedelic.R;
import com.bai.psychedelic.psychedelic.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2019/1/24.
 */

public class PhotoViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String>mData;
    private Picasso mPicasso;
    private Util mUtil;
    public PhotoViewPagerAdapter(Context context, List<String> list) {
        mContext = context;
        mData = list;
        mPicasso = new Picasso.Builder(mContext).build();
        mUtil = Util.getInstance(mContext);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.photo_viewer_page_item,null);
        ImageView mImageView = view.findViewById(R.id.photo_viewer_item_image);
        mPicasso.load(mData.get(position)).into(mImageView);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }
}
