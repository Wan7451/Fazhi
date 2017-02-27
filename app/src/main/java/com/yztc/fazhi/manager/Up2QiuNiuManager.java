package com.yztc.fazhi.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by wanggang on 2017/2/27.
 */

public class Up2QiuNiuManager {

    private Context context;
    private ArrayList<String> upFiles;
    private ArrayList<String> urls=new ArrayList<>();

    private ProgressDialog dialog;

    private OnUpFinishCallback l;

    public void setOnUpFinishCallback(OnUpFinishCallback l) {
        this.l = l;
    }

    public Up2QiuNiuManager(Context context, ArrayList<String> upFiles) {
        this.context = context;
        this.upFiles = upFiles;
    }


    int currUpFileIndex=0;

    //进行上传
    public void start(){
        this.dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        upFileToServer();
    }


    public void upFileToServer(){
        String upFile = upFiles.get(currUpFileIndex);
        dialog.setTitle("正在上传第"+(currUpFileIndex+1)+"张图片");
        dialog.setCancelable(false);
        zip(upFile);
    }

    private void zip(final String f) {
        Luban.get(context)
                .load(new File(f))           //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(File file) {
                        toServer(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        toServer(new File(f));
                    }
                }).launch();    //启动压缩
    }

    private void toServer(File file) {

       String key= generateKey(file);

        QiNiuUpManager.upLoadFile(context, file, key, false, new QiNiuUpManager.OnUpLoadLintener() {
            @Override
            public void onUpSuccess(String url) {
                //上传到服务器上的地址
                urls.add(url);
                next();
            }

            @Override
            public void progress(String key, double percent) {
                int progress=(int)(percent*100);
                dialog.setProgress(progress);
            }

            @Override
            public void onUpError(String msg) {
                next();
            }
        });
    }

    private String generateKey(File file) {
        return System.currentTimeMillis()
                +file.hashCode()
                +file.getName();
    }

    private void next() {
        currUpFileIndex++;
        if(currUpFileIndex==upFiles.size()){
            dialog.dismiss();
            if(l!=null){
                l.onUpFinished(urls);
            }
            return;
        }
        upFileToServer();
    }


    public interface OnUpFinishCallback{
        public void onUpFinished(ArrayList<String> urls);
    }

}
