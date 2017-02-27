package com.yztc.fazhi.ui.request.mvp;

import com.yztc.fazhi.base.BasePresenter;

import java.util.ArrayList;

/**
 * Created by wanggang on 2017/2/27.
 */

public interface IRequestPresenter extends BasePresenter{
    void choiceImages();
    void upImages();
    void deleteImage(String path);
     ArrayList<String> getSekectList();
}
