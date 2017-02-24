package com.yztc.fazhi.ui.login.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.jar.Pack200;

/**
 * Created by wanggang on 2017/2/23.
 */

public class UserBean {


    /**
     * user_id : 6
     * mobile : 13810985410
     * username : kkkk
     * nickname : 小明
     * icon : 头像
     * gender : 1
     * age : 20
     * province : 北京
     * city : 西城
     * trade : 行业
     * company : 公司
     * job : 职位
     * fullname : 真实姓名
     * balance : 0
     * session_key : ……
     */

    private int user_id;
    private String mobile;
    private String username;
    private String nickname;
    private String icon;
    private int gender;
    private int age;
    private String province;
    private String city;
    private String trade;
    private String company;
    private String job;
    private String fullname;
    private int balance;
    private String session_key;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
}
