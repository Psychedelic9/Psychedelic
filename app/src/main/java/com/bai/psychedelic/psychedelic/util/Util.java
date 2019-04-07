package com.bai.psychedelic.psychedelic.util;

import android.content.Context;

import com.bai.psychedelic.psychedelic.bean.H;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/7.
 */

public class Util {
    public static final int THUMBNAIL2BMIDDLE = 0;
    public static final int THUMBNAIL2ORIGINAL = 1;
    public static final int BMIDDLE2THUMBNAIL = 2;
    public static final int BMIDDLE2ORIGINAL = 3;
    public static final int ORIGINAL2BMIDDLE = 4;
    public static final int ORIGINAL2THUMBNAIL = 5;
    public static final int THUMBNAIL = 6;
    public static final int BMIDDLE = 7;
    public static final int ORIGINAL = 8;


    Context mContext;
    public Util(Context context) {
        mContext = context;
    }

    public static Util getInstance(Context context){
        return new Util(context);
    }

    public List<String> getStringUrlList(int type,List<H.StatusesBean.PicUrlsBeanX> urlsBeanList){
        List<String> mStringUrlList = new ArrayList<String>();
        String mPicUrl = "";
        if (urlsBeanList != null) {
            int size = urlsBeanList.size();
            for (int i = 0; i < size; i++) {
                 mPicUrl = urlsBeanList.get(i).getThumbnail_pic();
                if (type == THUMBNAIL) {
                    mStringUrlList.add(mPicUrl);
                } else if (type == BMIDDLE){
                    mStringUrlList.add(changePictureUrl(THUMBNAIL2BMIDDLE,mPicUrl));
                } else if (type == ORIGINAL){
                    mStringUrlList.add(changePictureUrl(THUMBNAIL2ORIGINAL,mPicUrl));
                }
            }
        }
            return mStringUrlList;
    }
    public String changePictureUrl(int type,String url){
        if (url == null){
            return "";
        }
        switch (type){
            case THUMBNAIL2BMIDDLE:
                return url.replace("thumbnail","bmiddle");
            case THUMBNAIL2ORIGINAL:
                return url.replace("thumbnail","original");
            case BMIDDLE2THUMBNAIL:
                return url.replace("bmiddle","thumbnail");
            case BMIDDLE2ORIGINAL:
                return url.replace("bmiddle","original");
            case ORIGINAL2BMIDDLE:
                return url.replace("original","bmiddle");
            case ORIGINAL2THUMBNAIL:
                return url.replace("original","thumbnail");

        }
        return "";
    }
}
