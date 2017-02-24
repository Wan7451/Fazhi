package com.yztc.fazhi.ui.login.mvp;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.yztc.fazhi.base.BaseView;
import com.yztc.fazhi.data.LocalData;
import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.net.NetConfig;
import com.yztc.fazhi.net.NetSubscriber;
import com.yztc.fazhi.net.RxHelper;
import com.yztc.fazhi.ui.login.bean.UserBean;
import com.yztc.fazhi.util.SPUtils;
import com.yztc.fazhi.util.UIManger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wanggang on 2017/2/22.
 */

public class LoginPresenterImpl implements ILoginPresenter {


    private ILoginModel module;
    private ILoginView view;

    public LoginPresenterImpl(ILoginView view) {
        this.module = new LoginModelImple();
        this.view = view;
    }

    @Override
    public void getCaptchaCode(final Button btn, String phone) {
        if(TextUtils.isEmpty(phone)){
            view.showError("手机号码不能为空");
            return;
        }

        if(!isPhoneNumb(phone)){
            view.showError("手机号码格式不正确");
            return;
        }


       final CountDownPresenterImpl countDownPresenter
               = new CountDownPresenterImpl();

        countDownPresenter.startCount(btn,10);

        Observable<BaseResponse<String>> captchaCode
                = module.getCaptchaCode(phone);

        captchaCode.compose(RxHelper.schedulers())
                    .compose(RxHelper.transform())
                    .subscribe(new NetSubscriber<String>() {
                        @Override
                        public BaseView getView() {
                            return view;
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            countDownPresenter.stopCount(btn);
                        }

                        @Override
                        public void onNext(String o) {
                            view.showHint("短信已发送发送");
                        }
                    });
    }

    private boolean isPhoneNumb(String phone) {
        //正则表达式
        return phone.length()==11;
    }


    @Override
    public void register(String phone, String pasd, String captcha) {

        if(TextUtils.isEmpty(phone)){
            view.showError("号码不能为空");
            return;
        }

        if(TextUtils.isEmpty(pasd)){
            view.showError("密码不能为空");
            return;
        }

        if(TextUtils.isEmpty(captcha)){
            view.showError("验证码不能为空");
            return;
        }

        if(!isPhoneNumb(phone)){
            view.showError("手机号码格式不正确");
            return;
        }

        if(captcha.length()!=4){
            view.showError("验证码输入错误");
            return;
        }

        Observable<BaseResponse<String>> register =
                module.register(phone, NetConfig.MD5(pasd), captcha);

        register.compose(RxHelper.schedulers())
                .compose(RxHelper.transform())
                .subscribe(new NetSubscriber() {
                    @Override
                    public BaseView getView() {
                        return view;
                    }

                    @Override
                    public void onNext(Object o) {
                        view.showHint("注册成功，重新进行登录");
                        //打开登陆界面
                        UIManger.startLogin(view.getContext());
                    }
                });
    }

    @Override
    public void login(String username, String pasd) {
        if(TextUtils.isEmpty(username)){
            view.showError("账号不能为空");
            return;
        }
        //wanggang
        //md5
        //ef342f07
        if(TextUtils.isEmpty(pasd)){
            view.showError("密码不能为空");
            return;
        }

        Observable<BaseResponse<UserBean>> login =
                module.login(username, NetConfig.MD5(pasd));

        login.compose(RxHelper.schedulers())
                .compose(RxHelper.transform())
                .subscribe(new NetSubscriber<UserBean>() {
                    @Override
                    public BaseView getView() {
                        return view;
                    }

                    @Override
                    public void onNext(UserBean uer) {
                        //登陆成功
                        LocalData.putSessionKey(uer.getSession_key());
                        LocalData.putUserID(uer.getUser_id());

                        UIManger.startUserInfo(view.getContext());
                    }
                });

    }
}
