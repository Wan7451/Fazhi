package cn.liweiqin.testselectphoto.core;


import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import cn.liweiqin.testselectphoto.model.PhotoInfo;
import cn.liweiqin.testselectphoto.ui.Callback;
import cn.liweiqin.testselectphoto.ui.weight.PhotoEditActivity;
import cn.liweiqin.testselectphoto.ui.weight.PhotoSelectActivity;
import cn.liweiqin.testselectphoto.utils.DeviceUtils;

/**
 * Created by liweiqin on 2016/1/31.
 */
public class PhotoFinal {

    public final static int REQUEST_CODE_CAMERA = 1000;//打开照相机的标识符
    public final static int REQUEST_CODE_MUTI = 1001;//打开相册的标识符

    private static FunctionConfig mCurrentFunctionConfig;
    private static OnHanlderResultCallback mCallback;
    private static Callback mSelectPhotoActivityCallback;

    public static void init(FunctionConfig coreConfig) {
        mCurrentFunctionConfig = coreConfig;

    }


    public static FunctionConfig getFunctionConfig() {
        return mCurrentFunctionConfig;
    }

    public static OnHanlderResultCallback getCallback() {
        return mCallback;
    }

    public static Callback getSelectPhotoActivityCallback() {
        return mSelectPhotoActivityCallback;
    }


    public interface OnHanlderResultCallback {
        /**
         * 处理成功
         *
         * @param reqeustCode
         * @param resultList
         */
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList);

        /**
         * 处理失败或异常
         *
         * @param requestCode
         * @param errorMsg
         */
        public void onHanlderFailure(int requestCode, String errorMsg);
    }

    /**
     * 拍照
     *
     * @param callback
     * @param mSelectCallback
     */
    public static void openCamera(OnHanlderResultCallback callback, Callback mSelectCallback) {

        if (mCurrentFunctionConfig == null) {
            if (callback != null) {
                callback.onHanlderFailure(REQUEST_CODE_CAMERA, "没有设置配置");
            }
            return;
        }

        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(mCurrentFunctionConfig.getContext(), "没有SD卡", Toast.LENGTH_SHORT).show();
            return;
        }


        mCallback = callback;
        mSelectPhotoActivityCallback = mSelectCallback;
        //打开拍照处理的Activity
        Intent intent = new Intent(mCurrentFunctionConfig.getContext(), PhotoEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCurrentFunctionConfig.getContext().startActivity(intent);

    }

    /**
     * 打开相册啦
     *
     * @param config
     * @param callback
     */
    public static void openMuti(FunctionConfig config, OnHanlderResultCallback callback) {

        if (config == null && mCurrentFunctionConfig == null) {
            if (callback != null) {
                callback.onHanlderFailure(REQUEST_CODE_MUTI, "没有设置配置");
            }
            return;
        }
        mCallback = callback;
        mCurrentFunctionConfig = config;

        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(mCurrentFunctionConfig.getContext(), "没有SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        //打开相册处理的Activity
        Intent intent = new Intent(mCurrentFunctionConfig.getContext(), PhotoSelectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCurrentFunctionConfig.getContext().startActivity(intent);

    }


}
