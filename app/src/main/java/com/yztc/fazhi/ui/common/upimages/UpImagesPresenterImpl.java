package com.yztc.fazhi.ui.common.upimages;

import android.content.Context;

import com.yztc.fazhi.dialog.LoadingDialog;
import com.yztc.fazhi.manager.QiNiuUpManager;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by wanggang on 2017/2/24.
 */

public class UpImagesPresenterImpl implements IUpImagesPresenter {

    private IUpImagesView view;
    private Context context;
    private LoadingDialog loadingDialog;

    public UpImagesPresenterImpl(Context context, IUpImagesView view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void upImage(final File img) {

        loadingDialog = new LoadingDialog(context);
        loadingDialog.show();
        loadingDialog.setMessage("压缩图片中...");
        Luban.get(context)
                .load(img)
                .putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        loadingDialog.setMessage("开始上传...");
                        up(context, file, img.getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.setMessage("开始上传...");
                        up(context, img, img.getName());
                    }
                }).launch();
    }

    private void up(Context context, File img, String name) {
        QiNiuUpManager.upLoadFile(context, img, name, true, new QiNiuUpManager.OnUpLoadLintener() {
            @Override
            public void onUpSuccess(String url) {
                loadingDialog.dismiss();
                view.onUpSuccess(url);
            }

            @Override
            public void progress(String key, double percent) {
                loadingDialog.setMessage("上传" + (int) (percent * 100 / 100) + "%");
            }

            @Override
            public void onUpError(String msg) {
                loadingDialog.dismiss();
                view.onUpError(msg);
            }
        });
    }

    @Override
    public void upImages(ArrayList<File> imgs) {

    }

}
