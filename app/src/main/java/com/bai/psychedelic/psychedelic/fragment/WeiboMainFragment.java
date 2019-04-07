package com.bai.psychedelic.psychedelic.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bai.psychedelic.psychedelic.R;
import com.bai.psychedelic.psychedelic.adapter.WeiboRecycleViewAdapter;
import com.bai.psychedelic.psychedelic.bean.H;
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

public class WeiboMainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView mWeiboRecyclerView;
    private List<H.StatusesBean> mList;
    private WeiboRecycleViewAdapter mRecycleAdapter;
    private Context mContext;
    private Oauth2AccessToken mToken;
    private Handler mHandler;
    private String mTokenString;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final String TAG = "WeiboMainFragment";
    private OnFragmentInteractionListener mListener;

    public WeiboMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeiboMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeiboMainFragment newInstance(String param1, String param2) {
        WeiboMainFragment fragment = new WeiboMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mContext = getContext();
    }
    Retrofit retrofit;
    GetRequest getRequest;
    Call<H> call;
    private void getNewDataFromServer() {
        PsyLog.d(TAG,"getNewDataFromServer()");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (retrofit==null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.weibo.com/2/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    getRequest = retrofit.create(GetRequest.class);
                    call = getRequest.getCall(mTokenString, 30,since_id,0);

                    call.enqueue(new Callback<H>() {
                        @Override
                        public void onResponse(Call<H> call, Response<H> response) {
                            PsyLog.d(TAG,"getNewDataFromServer onResponse response = "+ (response==null? "true":"false"));

                            if (response == null){
                                return;
                            }else {
                                if (response.body()==null){
                                    PsyLog.d(TAG,"response.body()==null");
                                    Toast.makeText(mContext,R.string.weibo_main_activity_no_new_data,Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (response.body().getStatuses().size()!=0) {
                                    mList.addAll(0,response.body().getStatuses());
                                    PsyLog.d(TAG,"getNewDataFromServer + new Statuses size = "
                                            +response.body().getStatuses().size()+"mList.size()"+mList.size());
                                    since_id = response.body().getSince_id();
                                    Message msg = mHandler.obtainMessage();
                                    msg.what = HandlerMessager.MSG_ADD_RECYCLER_DATA;
                                    msg.arg1 = response.body().getStatuses().size();
                                    mHandler.sendMessage(msg);
                                    PsyLog.d(TAG,"notifyDataSetChanged "+mList.get(0).getText());
                                }else {
                                    PsyLog.d(TAG,"getNewDataFromServer list == null");
                                    Toast.makeText(mContext,R.string.weibo_main_activity_no_new_data,Toast.LENGTH_SHORT).show();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<H> call, Throwable t) {
                            PsyLog.d(TAG,"getNewDataFromServer onFailure");
                        }
                    });
                }else {
                    if (call!=null){
                        call.clone().enqueue(new Callback<H>() {
                            @Override
                            public void onResponse(Call<H> call, Response<H> response) {
                                PsyLog.d(TAG,"getNewDataFromServer onResponse response = "+ (response==null? "true":"false"));

                                if (response == null){
                                    return;
                                }else {
                                    if (response.body()==null){
                                        PsyLog.d(TAG,"response.body()==null");
                                        Toast.makeText(mContext,R.string.weibo_main_activity_no_new_data,Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (response.body().getStatuses().size()!=0) {
                                        mList.addAll(0,response.body().getStatuses());
                                        PsyLog.d(TAG,"getNewDataFromServer + new Statuses size = "
                                                +response.body().getStatuses().size()+"mList.size()"+mList.size());
                                        since_id = response.body().getSince_id();
                                        Message msg = mHandler.obtainMessage();
                                        msg.what = HandlerMessager.MSG_ADD_RECYCLER_DATA;
                                        msg.arg1 = response.body().getStatuses().size();
                                        mHandler.sendMessage(msg);
                                        PsyLog.d(TAG,"notifyDataSetChanged "+mList.get(0).getText());
                                    }else {
                                        PsyLog.d(TAG,"getNewDataFromServer list == null");
                                        Toast.makeText(mContext,R.string.weibo_main_activity_no_new_data,Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<H> call, Throwable t) {
                                PsyLog.d(TAG,"getNewDataFromServer onFailure");
                            }
                        });
                    }
                }
            }
        }).start();
    }
    private long since_id = 0;
    private long max_id = 0;
    private void initDataFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mTokenString = (mToken.getToken().split("Token = "))[0];
                PsyLog.d(TAG, "TokenString = " + mTokenString);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.weibo.com/2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GetRequest getRequest = retrofit.create(GetRequest.class);
                Call<H> call = getRequest.getCall(mTokenString, 100,0,0);
                call.enqueue(new Callback<H>() {
                    @Override
                    public void onResponse(Call<H> call, Response<H> response) {
                        PsyLog.d(TAG, "onResponse()");
                        try {
                            if (response.body() != null) {
                                mList = response.body().getStatuses();
                                mHandler.sendEmptyMessage(HandlerMessager.MSG_RESPONSE_DATA);
                                since_id = response.body().getSince_id();
                                max_id = response.body().getMax_id();

                                PsyLog.d(TAG, "notifyDataSetChanged");
                                if (mList.size() != 0) {
                                    PsyLog.d(TAG, mList.size() + "");
                                } else {
                                    PsyLog.d(TAG, "mList is null!!!");
                                }

                            } else {
                                PsyLog.d(TAG, "null!!!!!");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<H> call, Throwable t) {
                        PsyLog.d(TAG, "onFailure()");
                        t.printStackTrace();
                    }

                });


            }
        }).start();
    }

    private Map<String, Object> getSendMap(String token, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("status", status);
        return map;
    }
    private void init(){
        mToken = AccessTokenKeeper.readAccessToken(mContext);
        mTokenString = (mToken.getToken().split("Token = "))[0];
        initDataFromServer();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case HandlerMessager.MSG_RESPONSE_DATA:
                        if (mWeiboRecyclerView.getAdapter() == null) {
                            mWeiboRecyclerView.setHasFixedSize(true);
                            mWeiboRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                            mWeiboRecyclerView.setAdapter(mRecycleAdapter = new WeiboRecycleViewAdapter(mContext, mList));
                        }
                        PsyLog.d(TAG,"getNewDataFromServer onResponse notifyDataSetChanged");
                        break;
                    case HandlerMessager.MSG_CLOSE_REFRESH:
                        mSwipeRefreshLayout.setRefreshing(false);
                        break;
                    case HandlerMessager.MSG_GET_NEW_DATA_FROM_SERVER:
                        getNewDataFromServer();
                        break;
                    case HandlerMessager.MSG_ADD_RECYCLER_DATA:
                        int count = msg.arg1;
                        mRecycleAdapter.notifyItemRangeChanged(0,count);
                        break;
                    default:
                        break;
                }
            }
        };


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        init();
        View view = inflater.inflate(R.layout.fragment_weibo_main, container, false);
        mWeiboRecyclerView = view.findViewById(R.id.weibo_main_recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.weibo_main_activity_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 设置可见
                mSwipeRefreshLayout.setRefreshing(true);
                // 重置adapter的数据源为空
                // 获取第第0条到第PAGE_COUNT（值为10）条的数据
                mHandler.sendEmptyMessage(HandlerMessager.MSG_GET_NEW_DATA_FROM_SERVER);
                mHandler.sendEmptyMessageDelayed(HandlerMessager.MSG_CLOSE_REFRESH, 1000);

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
