package com.yztc.fazhi.data;

import android.content.Context;

import com.yztc.fazhi.App;
import com.yztc.fazhi.util.SPUtils;

/**
 * Created by wanggang on 2017/2/22.
 */

public class LocalData {
    private static String splashImagePath;

    public static String getSessionKey() {
        return (String) SPUtils.get(App.getApp(),"SessionKey","");
    }

    public static void putSessionKey(String session_key) {
        SPUtils.put(App.getApp(),"SessionKey",session_key);
    }

    public static void putUserID(int user_id) {
        SPUtils.put(App.getApp(),"user_id",user_id);
    }

    public static int getUserID() {
      return (int) SPUtils.get(App.getApp(),"user_id",0);
    }

    public static String getSplashImagePath() {
        return (String) SPUtils.get(App.getApp(),"SplashImagePath","");
    }

    public static void putSplashImagePath(String pics) {
        SPUtils.put(App.getApp(),"SplashImagePath",pics);
    }
}
