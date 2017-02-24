package com.yztc.fazhi.base;

import android.content.Context;

/**
 * Created by wanggang on 2017/2/22.
 */

public interface BaseView {
    void showError(String error);
    void showLoading();
    void hideLoading();
    Context getContext();
}
