package com.yztc.fazhi.net;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.yztc.fazhi.data.LocalData;

import java.security.MessageDigest;

import okhttp3.MediaType;

/**
 * Created by wanggang on 2017/2/22.
 */

public class NetConfig {

    public static final String BASE_URL="http://52.220.113.22:8080/fazhi/";

    public static MediaType TypeJSON=MediaType.parse("application/json; charset=utf-8");


    private final static String ANDROID_APIKEY = "ybjkandroiddangmala";
    private final static String ANDROID_SECRETKEY = "jsdfji2o34IWE03!^e";


    public static JSONObject getBaseParams() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("api_key", ANDROID_APIKEY);
        jsonObj.put("timestamp", System.currentTimeMillis());
        jsonObj.put("v", "1.0");
        jsonObj.put("source", "android");
        jsonObj.put("sec_id", "MD5");
        jsonObj.put("format", "JSON");
        jsonObj.put("session_key", LocalData.getSessionKey());
        return jsonObj;
    }

    public static String getSign(JSONObject obj){
        return MD5(EncryptUtil.dataAnalysis(obj));
    }


    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
