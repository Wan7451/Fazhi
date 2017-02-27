package cn.liweiqin.testselectphoto.utils;

import android.app.Activity;
import android.os.Environment;
import android.util.DisplayMetrics;

/**
 * Created by liweiqin on 2016/1/31.
 */
public class DeviceUtils {
    public static boolean existSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static DisplayMetrics getScreenPix(Activity activity) {
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
        return displaysMetrics;
    }
}
