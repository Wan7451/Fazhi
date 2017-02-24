package com.yztc.fazhi.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.Enumeration;

/**
 * Created by wanggang on 2017/2/24.
 */
public class FileUtils {

    public static File getRootFile(Context context){
        if(Environment.isExternalStorageEmulated()){
             return context.getExternalCacheDir();
        }else {
            return context.getCacheDir();
        }
    }

    public static File getFolder(Context context,String name){
        File floder= new File(getRootFile(context),"image");
        if(!floder.exists()){
            floder.mkdirs();
        }
        return floder;
    }

    public static File getImageFolder(Context context){
        return getFolder(context,"images");
    }

    public static File getNetFolder(Context context){
        return getFolder(context,"net");
    }

    public static File getTempFolder(Context context){
        return getFolder(context,"temp");
    }


    public static File createTempFile(Context context,String fileName){
        File tempFile = getTempFolder(context);
        return new File(tempFile,fileName);
    }
}
