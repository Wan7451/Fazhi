package com.yztc.fazhi.ui.login.mvp;

import com.yztc.fazhi.ui.login.bean.UserBean;

/**
 * Created by wanggang on 2017/2/23.
 */

public interface IUserInfoPresenter {
    void getUserData();

    void updateUser(UserBean user);
}
