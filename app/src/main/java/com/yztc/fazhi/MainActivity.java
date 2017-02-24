package com.yztc.fazhi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONToken;
import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.net.NetConfig;
import com.yztc.fazhi.net.NetRequest;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String,Object> map=new HashMap<>();
        map.put("mobile","13935198850");
        RequestBody requestBody = NetRequest.generateReqBody(map);

        Observable<BaseResponse<String>> sendRegCode =
                NetRequest.getInstance().getApi().sendRegCode(requestBody);

        sendRegCode
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<BaseResponse<String>, String>() {
                    @Override
                    public String call(BaseResponse<String> baseResponse) {
                        if(baseResponse.is_success()){
                            return baseResponse.getData();
                        }else {
                            return null;
                        }
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("=======",e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("=======",s+"");
                    }
                });

    }
}
