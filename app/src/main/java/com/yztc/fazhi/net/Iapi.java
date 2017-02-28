package com.yztc.fazhi.net;

import com.yztc.fazhi.versioncheck.CkeckVersion;
import com.yztc.fazhi.ui.login.bean.UserBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wanggang on 2017/2/22.
 */

public interface Iapi {

    @POST("sendRegCode")
    Observable<BaseResponse<String>> sendRegCode(@Body RequestBody params);


    @POST("regUser")
    Observable<BaseResponse<String>> register(@Body RequestBody params);

    @POST("userLogin")
    Observable<BaseResponse<UserBean>> userLogin(@Body RequestBody params);


    @POST("getUser")
    Observable<BaseResponse<UserBean>> getUser(@Body RequestBody params);

    @POST("updateUser")
    Observable<BaseResponse<UserBean>> updateUser(@Body RequestBody params);

    @POST("versionCheck")
    Observable<BaseResponse<CkeckVersion>> versionCheck(@Body RequestBody params);


}
