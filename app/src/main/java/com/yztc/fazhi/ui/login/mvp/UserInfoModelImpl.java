package com.yztc.fazhi.ui.login.mvp;

import com.yztc.fazhi.data.LocalData;
import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.net.NetRequest;
import com.yztc.fazhi.ui.login.bean.UserBean;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by wanggang on 2017/2/23.
 */

public class UserInfoModelImpl implements IUserInfoModel {
    @Override
    public Observable<BaseResponse<UserBean>> getUser(int userId) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("user_id",userId);
        RequestBody requestBody = NetRequest.generateReqBody(map);
        return NetRequest.getInstance().getApi().getUser(requestBody);
    }

    @Override
    public Observable<BaseResponse<UserBean>> updateUser(String nickname, String icon, int gender, int age, String province, String city, String trade, String company, String job, String fullname) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("user_id", LocalData.getUserID());
        map.put("nickname",nickname);
        map.put("icon",icon);
        map.put("gender",gender);
        map.put("age",age);
        map.put("province",province);
        map.put("city",city);
        map.put("trade",trade);
        map.put("company",company);
        map.put("job",job);
        map.put("fullname",fullname);
        RequestBody requestBody = NetRequest.generateReqBody(map);
        return NetRequest.getInstance().getApi().updateUser(requestBody);

    }
}
