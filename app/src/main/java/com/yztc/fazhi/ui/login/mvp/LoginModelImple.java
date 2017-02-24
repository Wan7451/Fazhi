package com.yztc.fazhi.ui.login.mvp;

import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.net.NetRequest;
import com.yztc.fazhi.ui.login.bean.UserBean;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by wanggang on 2017/2/22.
 */

public class LoginModelImple implements ILoginModel {


    @Override
    public Observable<BaseResponse<String>> getCaptchaCode(String phone) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("mobile",phone);
        RequestBody requestBody = NetRequest.generateReqBody(map);
        return NetRequest.getInstance().getApi().sendRegCode(requestBody);
    }

    @Override
    public Observable<BaseResponse<String>> register(String phone, String pasd, String captcha) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("mobile",phone);
        map.put("password",pasd);
        map.put("verify_code",captcha);
        RequestBody requestBody = NetRequest.generateReqBody(map);
        return NetRequest.getInstance().getApi().register(requestBody);

    }

    @Override
    public Observable<BaseResponse<UserBean>> login(String username, String pasd) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("username",username);
        map.put("password",pasd);
        RequestBody requestBody = NetRequest.generateReqBody(map);
        return NetRequest.getInstance().getApi().userLogin(requestBody);
    }
}
