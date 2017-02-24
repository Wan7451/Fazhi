package com.yztc.fazhi.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wanggang on 2017/2/23.
 */


 //A     a
 //B

public class ToastUtils {

    public static Toast toast;

    public static void showToast(Context context,String text){
        if(toast==null){
            toast=Toast.makeText(context.getApplicationContext(),
                    text,Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showErrorToast(Context context,String text){
        if(toast==null){
            toast=Toast.makeText(context.getApplicationContext(),
                    text,Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }



}
