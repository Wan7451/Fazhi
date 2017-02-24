package com.yztc.fazhi.net;

/**
 * Created by wanggang on 2017/2/22.
 */

public class BaseResponse<T> {

    private boolean is_success;
    private String error_content;
    private T data;

    public boolean is_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public String getError_content() {
        return error_content;
    }

    public void setError_content(String error_content) {
        this.error_content = error_content;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
