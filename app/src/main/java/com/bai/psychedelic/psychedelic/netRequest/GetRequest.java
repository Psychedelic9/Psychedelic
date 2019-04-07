package com.bai.psychedelic.psychedelic.netRequest;


import com.bai.psychedelic.psychedelic.bean.H;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/12/23.
 */

public interface GetRequest {
    @GET("statuses/friends_timeline.json")
    Call<H> getCall(@Query("access_token")String token,@Query("count")int count,
                    @Query("since_id")long sinceId,@Query("max_id")long maxId);

}
