package com.yztc.fazhi.net;


import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author tom
 */
public class EncryptUtil {
    public final static String ANDROID_APIKEY = "ybjkandroiddangmala";
    public final static String ANDROID_SECRETKEY = "jsdfji2o34IWE03!^e";

    /**
     * 解析数据,返回String数据串,数据为null部分不处理
     *
     * @return 数据串
     */
    public static String dataAnalysis(JSONObject obj) {
        StringBuilder sb = new StringBuilder();
        List<String> fieList = new ArrayList<String>();

        //取出 提交数据中的key
        Iterator it = obj.keySet().iterator();
        while (it.hasNext()) {
            fieList.add(it.next().toString());
        }

        //对key 进行排序
        //根据对象属性名称排序
        ComparatorName comparator = new ComparatorName();
        Collections.sort(fieList, comparator);


        //根据key 取出对应的数据，进行拼接
        for (String s : fieList) {
            Object objData = obj.get(s).toString();
            if (objData != null) {
                if (objData instanceof String) {
                    if (TextUtils.isEmpty((String) objData)) {
                        continue;
                    }
                }
                sb.append(s + "=" + objData);
            }
        }

        sb.append(ANDROID_SECRETKEY);
        return sb.toString();
    }


    public static class ComparatorName implements Comparator<Object> {
        public int compare(Object arg0, Object arg1) {
            String name0 = (String) arg0;
            String name1 = (String) arg1;

            // 比较名字排序
            return name0.compareTo(name1);
        }

    }



}
