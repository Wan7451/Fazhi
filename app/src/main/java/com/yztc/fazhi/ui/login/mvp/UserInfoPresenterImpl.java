package com.yztc.fazhi.ui.login.mvp;

import android.text.TextUtils;

import com.yztc.fazhi.base.BaseView;
import com.yztc.fazhi.data.LocalData;
import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.net.NetSubscriber;
import com.yztc.fazhi.net.RxHelper;
import com.yztc.fazhi.ui.login.bean.UserBean;

import rx.Observable;

/**
 * Created by wanggang on 2017/2/23.
 */

public class UserInfoPresenterImpl implements IUserInfoPresenter {

    private IUserInfoModel model;
    private IUserInfoView view;

    public UserInfoPresenterImpl(IUserInfoView view) {
        this.view = view;
        model = new UserInfoModelImpl();
    }

    @Override
    public void getUserData() {
        int userID = LocalData.getUserID();

        Observable<BaseResponse<UserBean>> user
                = model.getUser(userID);

        user.compose(RxHelper.transform())
                .compose(RxHelper.schedulers())
                .subscribe(new NetSubscriber<UserBean>() {
                    @Override
                    public BaseView getView() {
                        return view;
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        view.showUserData(userBean);
                    }
                });

    }

    @Override
    public void updateUser(UserBean user) {
        Observable<BaseResponse<UserBean>> updateUser
                = model.updateUser(user.getNickname(),
                user.getIcon(),
                user.getGender(),
                user.getAge(),
                user.getProvince(),
                user.getCity(),
                user.getTrade(),
                user.getCompany(),
                user.getJob(),
                user.getFullname());

        updateUser.compose(RxHelper.schedulers())
                .compose(RxHelper.transform())
                .subscribe(new NetSubscriber() {
                    @Override
                    public BaseView getView() {
                        return view;
                    }

                    @Override
                    public void onNext(Object o) {
                        view.showError("ok");
                    }
                });
    }


}
