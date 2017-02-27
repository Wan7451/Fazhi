package cn.liweiqin.testselectphoto;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import cn.liweiqin.testselectphoto.utils.DeviceUtils;
import cn.liweiqin.testselectphoto.utils.MediaScanner;

/**
 * Created by liweiqin on 2016/1/31.
 */
public abstract class BasePhotoActivity extends Activity {

    protected int mScreenWidth = 720;
    protected int mScreenHeight = 1280;

    protected MediaScanner mMediaScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMediaScanner = new MediaScanner(this);
        DisplayMetrics dm = DeviceUtils.getScreenPix(this);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
    }
}
