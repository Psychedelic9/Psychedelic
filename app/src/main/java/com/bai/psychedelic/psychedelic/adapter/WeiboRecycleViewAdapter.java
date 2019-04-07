package com.bai.psychedelic.psychedelic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bai.psychedelic.psychedelic.R;
import com.bai.psychedelic.psychedelic.bean.H;
import com.bai.psychedelic.psychedelic.util.CircleTransform;
import com.bai.psychedelic.psychedelic.util.DataUtil;
import com.bai.psychedelic.psychedelic.util.StringUtil;
import com.bai.psychedelic.psychedelic.util.Util;
import com.bai.psychedelic.psychedelic.widget.NineGridLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/12/23.
 */

public class WeiboRecycleViewAdapter extends RecyclerView.Adapter<WeiboRecycleViewAdapter.MyHolder> {
    Context mContext;
    List<H.StatusesBean>mList;
    Picasso mPicasso;
    Util mUtil;
    List<H.StatusesBean.PicUrlsBeanX> mUrlList;
    public WeiboRecycleViewAdapter(Context context, List<H.StatusesBean> list) {
        this.mContext = context;
        this.mList = list;
        mUtil = Util.getInstance(mContext);
        if (mPicasso==null) {
            mPicasso = new Picasso.Builder(mContext).build();//这里可以使用单例模式包装一下
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weibo_recycle_view_item
        ,parent,false);
        MyHolder myHolder = new MyHolder(mView);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (mList.get(position)!=null){
            H.StatusesBean mStatuses = mList.get(position);
            holder.mTextTextView.setText(mStatuses.getText());
            holder.mSourceTextView.setText(StringUtil.getWeiboSource(mStatuses.getSource()));
            holder.mCreateAtTextView.setText(DataUtil.showTime(mStatuses.getCreated_at()));
            holder.mUserNameTextView.setText(mStatuses.getUser().getName());
            mPicasso.load(mStatuses.getUser().getProfile_image_url())
                    .transform(new CircleTransform()).into(holder.mUserIconImageView);
            if (mStatuses.getReposts_count()!=0){
                holder.mRepostsCount.setText(mStatuses.getReposts_count()+"");
            }
            if (mStatuses.getComments_count()!=0){
                holder.mCommentsCount.setText(mStatuses.getComments_count()+"");
            }
            if (mStatuses.getAttitudes_count()!=0){
                holder.mAttitudesCount.setText(mStatuses.getAttitudes_count()+"");
            }
            if (mStatuses.getRetweeted_status()!=null){
                holder.mRetweetedStatusTextView.setVisibility(View.VISIBLE);
                H.StatusesBean.RetweetedStatusBean mRetweetedStatus = mList.get(position).getRetweeted_status();
                String mRetweetedStatusText = mRetweetedStatus.getUser().getName()
                        +": "+mRetweetedStatus.getText();
                holder.mRetweetedStatusTextView.setText(mRetweetedStatusText);
                if (mStatuses.getRetweeted_status().getPic_urls()!=null){
                    holder.mNineGridLayout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
                    holder.mNineGridLayout.setSpacing(10); //动态设置图片之间的间隔
                    holder.mNineGridLayout.setUrlList(mUtil.getStringUrlList(mUtil.BMIDDLE,mUrlList)); //最后再设置图片url
                }
            }else {
                holder.mRetweetedStatusTextView.setVisibility(View.INVISIBLE);
            }
            mUrlList = mList.get(position).getPic_urls();
            if (mStatuses.getPic_urls()!=null){
                holder.mNineGridLayout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
                holder.mNineGridLayout.setSpacing(10); //动态设置图片之间的间隔
                holder.mNineGridLayout.setUrlList(mUtil.getStringUrlList(mUtil.BMIDDLE,mUrlList)); //最后再设置图片url
            }
        }

        //控件监听
        holder.mTextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //item监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView mTextTextView;
        private TextView mRetweetedStatusTextView;
        private TextView mUserNameTextView;
        private TextView mCreateAtTextView;
        private TextView mSourceTextView;
        private ImageView mUserIconImageView;
        private NineGridLayout mNineGridLayout;
        private TextView mRepostsCount;
        private TextView mCommentsCount;
        private TextView mAttitudesCount;

        public MyHolder(View itemView) {
            super(itemView);
            mTextTextView = itemView.findViewById(R.id.weibo_recycle_view_item_text);
            mRetweetedStatusTextView = itemView.findViewById(R.id.weibo_recycle_view_item_retweeted_status);
            mUserNameTextView = itemView.findViewById(R.id.weibo_recycle_view_item_user_name);
            mCreateAtTextView = itemView.findViewById(R.id.weibo_recycle_view_item_create_at);
            mSourceTextView = itemView.findViewById(R.id.weibo_recycle_view_item_source);
            mUserIconImageView = itemView.findViewById(R.id.weibo_recycle_view_item_head_sculpture);
            mNineGridLayout = itemView.findViewById(R.id.weibo_recycle_view_item_layout_nine_grid);
            mRepostsCount = itemView.findViewById(R.id.weibo_recycle_view_item_reposts_count_text);
            mCommentsCount = itemView.findViewById(R.id.weibo_recycle_view_item_comments_count_text);
            mAttitudesCount = itemView.findViewById(R.id.weibo_recycle_view_item_attitudes_count_text);

        }
    }
}
