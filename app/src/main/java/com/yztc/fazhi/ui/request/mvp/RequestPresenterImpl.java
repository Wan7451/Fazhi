package com.yztc.fazhi.ui.request.mvp;

import android.content.Context;

import com.yztc.fazhi.MainActivity;
import com.yztc.fazhi.manager.QiNiuUpManager;
import com.yztc.fazhi.manager.Up2QiuNiuManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.liweiqin.testselectphoto.core.FunctionConfig;
import cn.liweiqin.testselectphoto.core.PhotoFinal;
import cn.liweiqin.testselectphoto.model.PhotoInfo;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by wanggang on 2017/2/27.
 */

public class RequestPresenterImpl implements IRequestPresenter {
    private IRequestView view;
    private ArrayList<String> sekectList = new ArrayList<>();

    public RequestPresenterImpl(IRequestView view) {
        this.view = view;
    }

    @Override
    public void choiceImages() {
        //相片浏览配置
        FunctionConfig functionConfig = initConfig();
        //打开图片 选择界面
        PhotoFinal.openMuti(functionConfig, mOnHanlderResultCallback);
    }

    @Override
    public void upImages() {
        //上传
        Up2QiuNiuManager manager=new Up2QiuNiuManager(view.getContext(),sekectList);
        manager.setOnUpFinishCallback(new Up2QiuNiuManager.OnUpFinishCallback() {
            @Override
            public void onUpFinished(ArrayList<String> urls) {
                view.onUped2Server(urls);
            }
        });
        manager.start();
    }


    public ArrayList<String> getSekectList() {
        return sekectList;
    }

    @Override
    public void deleteImage(String path) {
        if (sekectList != null){
            sekectList.remove(path);
            view.onShowChoicedImages(sekectList);
        }
    }

    /**
     * 加载配置的信息
     */
    private FunctionConfig initConfig() {
        //对 选择图片进行配置
        FunctionConfig.Builder functionBuilder = new FunctionConfig.Builder();
        FunctionConfig functionConfig = functionBuilder.setMaxSize(9)//设置最大选择数
                .setSelected(sekectList)//设置选泽的照片集
                .setContext(view.getContext())//设置上下文对象
                .setTakePhotoFolder(null)//设置拍照存放地址 默认为null
                .build();
        PhotoFinal.init(functionConfig);
        return functionConfig;
    }

    /**
     * 选择好图片的回调
     */
    private PhotoFinal.OnHanlderResultCallback mOnHanlderResultCallback = new PhotoFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (reqeustCode == PhotoFinal.REQUEST_CODE_MUTI) {
                //是选择图片回来的照片
                sekectList.clear();
                for (PhotoInfo info : resultList) {
                    sekectList.add(info.getPhotoPath());
                }
                view.onShowChoicedImages(sekectList);
            } else if (reqeustCode == PhotoFinal.REQUEST_CODE_CAMERA) {
                //是拍照带回来的照片
                sekectList.add(resultList.get(0).getPhotoPath());
                view.onShowChoicedImages(sekectList);
            }

        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            view.showError(errorMsg);
        }
    };
}
