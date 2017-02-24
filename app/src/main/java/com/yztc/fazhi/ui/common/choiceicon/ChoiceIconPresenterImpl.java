package com.yztc.fazhi.ui.common.choiceicon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yztc.fazhi.data.LocalData;
import com.yztc.fazhi.util.FileUtils;
import com.yztc.fazhi.util.ToastUtils;

import java.io.File;

import rx.functions.Action1;

/**
 * Created by wanggang on 2017/2/24.
 */

public class ChoiceIconPresenterImpl implements IChoiceIconPresenter {
    private static final int REQUEST_CAMERA = 111;
    private static final int REQUEST_GALLERY = 222;
    private static final int REQUEST_CROP = 333;
    private File mTmpFile;
    private File mCropedFile;

    private Activity activity;
    private IChoiceIconView view;


    public ChoiceIconPresenterImpl(Activity activity, IChoiceIconView view) {
        this.activity = activity;
        this.view = view;
    }

    @Override
    public void openCamera() {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            goCamera();
                        } else {
                            ToastUtils.showToast(activity, "请授权");
                        }
                    }
                });

    }

    private void goCamera() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            mTmpFile = FileUtils.createTempFile(activity, generateFileName());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            activity.startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            ToastUtils.showErrorToast(activity, "不能打开相机");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                //请求相机
                if (mTmpFile != null) {
                    mCropedFile = FileUtils.createTempFile(activity, generateFileName());
                    Uri outUri = Uri.fromFile(mCropedFile);
                    Uri inUri = Uri.fromFile(mTmpFile);
                    crop(inUri, outUri);
                }
                break;
            case REQUEST_GALLERY:
                if(data!=null){
                    Uri inUri = data.getData();
                    mCropedFile = FileUtils.createTempFile(activity, generateFileName());
                    Uri outUri = Uri.fromFile(mCropedFile);
                    crop(inUri, outUri);
                }
                break;
            case REQUEST_CROP:
                view.showCropImage(mCropedFile);
                break;
        }
    }


    public void crop(Uri inUri, Uri outUri) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(inUri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, REQUEST_CROP);
    }

    @Override
    public void openGallery() {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            goGallery();
                        } else {
                            ToastUtils.showToast(activity, "请授权");
                        }
                    }
                });

    }

    private void goGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }

    //防止 图片命名相同
    public String generateFileName() {
        return System.currentTimeMillis() + LocalData.getSessionKey();
    }
}
