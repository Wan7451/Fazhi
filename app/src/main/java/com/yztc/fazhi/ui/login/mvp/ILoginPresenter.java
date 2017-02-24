package com.yztc.fazhi.ui.login.mvp;

import android.widget.Button;

/**
 * Created by wanggang on 2017/2/22.
 */

public interface ILoginPresenter {
    //验证码
    void getCaptchaCode(Button btn,String phone);
    //注册
    void register(String phone,String pasd,String captcha);
    //登陆
    void login(String username,String pasd);
}
