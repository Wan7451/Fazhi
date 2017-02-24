package com.yztc.fazhi.loader;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.util.LruCache;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yztc.fazhi.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by wanggang on 2017/2/24.
 */

public class ImageLoader {
    //LruCache     Lru
    //SoftCache    SoftReference
    //DiskCache    外部存储器  内部存储器
    //NET

    public static void loadIcon(Activity activity, String path, ImageView imgView){
        Glide.with(activity)
                .load(path)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new CropCircleTransformation(activity))
                .into(imgView);
    }


    @BindingAdapter({"iconUrl"})
    public static void loadIconUrl(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new CropCircleTransformation(view.getContext()))
                .into(view);
    }



    public static void loadImgs(Activity activity, String path, ImageView imgView){
        Glide.with(activity)
                .load(path)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgView);
    }

    public static void loadImgs(Fragment fragment, String path, ImageView imgView){
        Glide.with(fragment)
                .load(path)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgView);
    }

}
