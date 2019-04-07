package com.bai.psychedelic.psychedelic.util;

import android.util.Log;

/**
 * Created by Administrator on 2018/12/29.
 */

public class PsyLog  {
    private static boolean psylog = true;
    public static void d(String tag,String text){
        if (psylog){
            Log.d(tag,text);
        }
    }
}
