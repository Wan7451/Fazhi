package com.yztc.fazhi;

import android.app.Application;

import com.example.myapp.MyEventBusIndex;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by wanggang on 2017/2/23.
 */

public class App extends Application {

    private static App app;

    public static App getApp(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;

        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }
}
