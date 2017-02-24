package com.yztc.fazhi.ui.login.mvp;

import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.ui.login.bean.UserBean;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by wanggang on 2017/2/23.
 */

public interface IUserInfoModel {
    //获取用户数据
    Observable<BaseResponse<UserBean>> getUser(int userId);
    //更新资料
    Observable<BaseResponse<UserBean>> updateUser(
            String nickname,
            String icon,
            int gender,
            int age,
            String province,
            String city,
            String trade,
            String company,
            String job,
            String fullname);
}
