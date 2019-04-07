package com.bai.psychedelic.psychedelic.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bai.psychedelic.psychedelic.R;
import com.bai.psychedelic.psychedelic.adapter.WeiboRecycleViewAdapter;
import com.bai.psychedelic.psychedelic.bean.H;
import com.bai.psychedelic.psychedelic.fragment.WeiboMainFragment;
import com.bai.psychedelic.psychedelic.netRequest.GetRequest;
import com.bai.psychedelic.psychedelic.util.HandlerMessager;
import com.bai.psychedelic.psychedelic.util.PsyLog;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeiboMainActivity extends AppCompatActivity implements WeiboMainFragment.OnFragmentInteractionListener{
    private Context mContext;
    private Oauth2AccessToken mToken;
    private WeiboMainFragment mMainFragment;
    private FragmentManager mFragmentManager;
    private final String TAG = "WeiboMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_main);
        init();


    }



    @Override
    protected void onResume() {
        super.onResume();
//        mHandler.sendEmptyMessage(HandlerMessager.MSG_GET_NEW_DATA_FROM_SERVER);
    }


    private void init() {
        PsyLog.d(TAG, "init()");
        mContext = this;
        mMainFragment = new WeiboMainFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.weibo_main_fragment,mMainFragment).commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
