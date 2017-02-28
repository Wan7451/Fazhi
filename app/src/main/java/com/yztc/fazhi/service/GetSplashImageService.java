package com.yztc.fazhi.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.yztc.fazhi.data.LocalData;
import com.yztc.fazhi.net.NetRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wanggang on 2017/2/28.
 */

public class GetSplashImageService extends IntentService {
    public GetSplashImageService() {
        super(GetSplashImageService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String path="http://mapi.damai.cn/Other/WelcomePagePicv1.aspx?appType=1&source=10101&channel_from=xiaomi_market&osType=2&type=720_1280&cityid=852&version=50609";
        OkHttpClient httpClient =
                NetRequest.getInstance().getHttpClient();

        Request request=new Request.Builder().url(path).build();

        try {
            Response response = httpClient.newCall(request).execute();
            String data = response.body().string();
            JSONObject object=new JSONObject(data);
            String pics = object.getString("pic");

            int widthPixels = getResources().getDisplayMetrics().widthPixels;
            int heightPixels = getResources().getDisplayMetrics().heightPixels;
            Glide.with(this).load(pics).downloadOnly(widthPixels,heightPixels);

            LocalData.putSplashImagePath(pics);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void start(Context context) {
        context.startService(new Intent(context,GetSplashImageService.class));
    }
}
