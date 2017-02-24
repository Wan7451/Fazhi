package com.yztc.fazhi.ui.login.mvp;

import android.os.Handler;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wanggang on 2017/2/23.
 */

public class CountDownPresenterImpl implements ICountDownPresenter {


    private Handler handler = new Handler();
    private Timer timer;
    private int count = 0;

    @Override
    public void startCount(final Button btn, final int duration) {

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        btn.setEnabled(false);
                        btn.setText(duration - count + "s");
                        if (duration - count == 0) {
                            btn.setEnabled(true);
                            btn.setText("发送验证码");
                            timer.cancel();
                        }
                    }
                });
                count++;
            }
        }, 0, 1000);
    }

    @Override
    public void stopCount(Button btn) {
        if (timer != null) {
            timer.cancel();
            btn.setEnabled(true);
            btn.setText("发送验证码");
        }
    }
}
