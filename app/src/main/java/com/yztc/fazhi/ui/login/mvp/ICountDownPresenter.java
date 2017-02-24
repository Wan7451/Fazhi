package com.yztc.fazhi.ui.login.mvp;

import android.widget.Button;

/**
 * Created by wanggang on 2017/2/23.
 */

public interface ICountDownPresenter {
    void startCount(Button btn,int duration);
    void stopCount(Button btn);
}
