package com.yztc.fazhi.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yztc.fazhi.MainActivity;
import com.yztc.fazhi.R;
import com.yztc.fazhi.data.LocalData;
import com.yztc.fazhi.service.GetSplashImageService;
import com.yztc.fazhi.ui.login.LoginActivity;
import com.yztc.fazhi.util.UIManger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_img)
    ImageView splashImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        String path=LocalData.getSplashImagePath();
        if(!TextUtils.isEmpty(path)){
            Glide.with(this)
                    .load(path)
                    .into(splashImg);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                GetSplashImageService.start(SplashActivity.this);

                if(TextUtils.isEmpty(LocalData.getSessionKey())){
                    UIManger.startLogin(SplashActivity.this);
                }else {
                    UIManger.startMain(SplashActivity.this);
                }

                finish();
            }
        },2000);
    }
}
