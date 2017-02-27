package com.yztc.fazhi.ui.request.mvp;

import com.yztc.fazhi.base.BaseView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by wanggang on 2017/2/27.
 */

public interface IRequestView extends BaseView {
    void onShowChoicedImages(ArrayList<String> imgs);
    void onUped2Server(ArrayList<String> url);
}
