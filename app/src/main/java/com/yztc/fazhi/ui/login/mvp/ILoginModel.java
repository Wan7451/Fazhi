package com.yztc.fazhi.ui.login.mvp;

import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.ui.login.bean.UserBean;

import rx.Observable;

/**
 * Created by wanggang on 2017/2/22.
 */

public interface ILoginModel {
    //验证码
    Observable<BaseResponse<String>> getCaptchaCode(String phone);
    //注册
    Observable<BaseResponse<String>> register(String phone, String pasd, String captcha);
    //登陆
    Observable<BaseResponse<UserBean>> login(String username, String pasd);

}
