package com.yztc.fazhi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yztc.fazhi.net.BaseResponse;
import com.yztc.fazhi.net.NetRequest;
import com.yztc.fazhi.net.RxHelper;
import com.yztc.fazhi.service.DownoladService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;
import permissions.dispatcher.RuntimePermissions;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkVersion();

        EventBus.getDefault().register(this);
    }


    private void checkVersion(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("version",getVersion());
        RequestBody requestBody = NetRequest.generateReqBody(map);

        Observable<BaseResponse<CkeckVersion>> checkVersion =
                NetRequest.getInstance().getApi().versionCheck(requestBody);

        checkVersion
                .delay(3, TimeUnit.SECONDS)
                .compose(RxHelper.schedulers())
                .compose(RxHelper.transform())
                .subscribe(new Subscriber<CkeckVersion>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CkeckVersion result) {
                            int version =Integer.parseInt(result.getVersion());
                            version =2;
                            if(version>getVersion()){
                                //检测到新版本，进行提示用户，下载
                                //当用户点击下载，进行下载安装
                                RxPermissions permissions=new RxPermissions(MainActivity.this);
                                permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        .subscribe(new Action1<Boolean>() {
                                            @Override
                                            public void call(Boolean aBoolean) {
                                                String path="http://oh0vbg8a6.bkt.clouddn.com/app-debug.apk";
                                                startDown(path);
                                            }
                                        });

                            }

                    }

                });
    }


    private void startDown(String path) {
        DownoladService.startDown(MainActivity.this,path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewVersionDown(EventNewVersion e){
        String file = e.getNewFile();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(file)), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private int getVersion() {
        int versionCode=1;
        PackageManager packageManager = getPackageManager();
        String packageName = getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (packageInfo != null) {
            // 这里就拿到版本信息了。
             versionCode = packageInfo.versionCode;
        }
        return versionCode;
    }

}
