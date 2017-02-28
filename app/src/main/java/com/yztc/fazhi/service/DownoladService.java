package com.yztc.fazhi.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.yztc.fazhi.EventNewVersion;
import com.yztc.fazhi.R;
import com.yztc.fazhi.net.NetRequest;
import com.yztc.fazhi.util.FileUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wanggang on 2017/2/28.
 */

public class DownoladService extends IntentService {

    public DownoladService() {
        super(DownoladService.class.getName());
    }

    public static void startDown(Context context,String path){
        Intent i=new Intent();
        i.setClass(context,DownoladService.class);
        i.putExtra("path",path);
        context.startService(i);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        String path = intent.getStringExtra("path");
        OkHttpClient httpClient = NetRequest.getInstance().getHttpClient();

        Request down=new Request.Builder()
                .url(path)
                .build();
        BufferedInputStream buffIn = null;
        BufferedOutputStream buffOut = null;
        try {
            Response response = httpClient.newCall(down).execute();
            InputStream in = response.body().byteStream();
             buffIn=new BufferedInputStream(in);

            final File file=new File(FileUtils.getNetFolder(this),getResources().getString(R.string.app_name)+".apk");
            OutputStream out=new FileOutputStream(file);
             buffOut=new BufferedOutputStream(out);

            int len;
            byte[] b=new byte[40*1024];

            while ((len=buffIn.read(b))!=-1){
                buffOut.write(b,0,len);
            }
            buffOut.flush();

            EventBus.getDefault().post(new EventNewVersion(file.getAbsolutePath()));


//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setDataAndType(Uri.fromFile(new File(file)), "application/vnd.android.package-archive");
//            startActivity(i);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(buffIn!=null){
                try {
                    buffIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(buffOut!=null){
                try {
                    buffOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
