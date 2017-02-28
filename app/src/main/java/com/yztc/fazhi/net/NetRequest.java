package com.yztc.fazhi.net;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wanggang on 2017/2/22.
 */

public class NetRequest {

    private static final String TAG = "===NET REQUEST===";
    private static NetRequest instance;
    private OkHttpClient client;
    private Retrofit retrofit;
    private Iapi iapi;

    public static NetRequest getInstance() {
        if (instance == null) {
            synchronized (NetRequest.class) {
                if (instance == null) {
                    instance = new NetRequest();
                }
            }
        }
        return instance;
    }

    public NetRequest() {
        client = getClient();
        retrofit = getRetrofit();

    }

    private OkHttpClient getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }


    public OkHttpClient getHttpClient(){
        return client;
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(NetConfig.BASE_URL)
                .build();
    }


    public Iapi getApi() {
        if (iapi == null) {
            iapi = retrofit.create(Iapi.class);
        }
        return iapi;
    }



    public  static RequestBody generateReqBody(HashMap<String,Object> map){
        JSONObject params = NetConfig.getBaseParams();
        params.putAll(map);
        params.put("sign",NetConfig.getSign(params));
        return RequestBody.create(NetConfig.TypeJSON,
                params.toJSONString());
    }
}
