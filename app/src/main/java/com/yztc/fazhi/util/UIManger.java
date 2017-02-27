package com.yztc.fazhi.util;

import android.content.Context;
import android.content.Intent;

import com.yztc.fazhi.ui.login.LoginActivity;
import com.yztc.fazhi.ui.login.UserInfoActivity;
import com.yztc.fazhi.ui.request.GetImagesActivity;

import java.util.ArrayList;

/**
 * Created by wanggang on 2017/2/23.
 */

public class UIManger {

    public static void startLogin(Context context){
        Intent i=new Intent();
        i.setClass(context,LoginActivity.class);
        context.startActivity(i);
    }

    public static void startUserInfo(Context context){
        Intent i=new Intent();
        i.setClass(context,UserInfoActivity.class);
        context.startActivity(i);
    }

    public static void startImageList(Context context, ArrayList<String> imgs){
        Intent i=new Intent();
        i.putExtra("imgs",imgs);
        i.setClass(context,GetImagesActivity.class);
        context.startActivity(i);
    }

}
