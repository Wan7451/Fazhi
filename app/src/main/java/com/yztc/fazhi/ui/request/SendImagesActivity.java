package com.yztc.fazhi.ui.request;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.yztc.fazhi.R;
import com.yztc.fazhi.ui.request.mvp.IRequestPresenter;
import com.yztc.fazhi.ui.request.mvp.IRequestView;
import com.yztc.fazhi.ui.request.mvp.RequestPresenterImpl;
import com.yztc.fazhi.util.UIManger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendImagesActivity extends AppCompatActivity implements IRequestView{

    @BindView(R.id.request_grid_choicedImages)
    GridView requestGridChoicedImages;

    @BindView(R.id.request_btn_upImages)
    Button requestBtnUpImages;

    private RequestPresenterImpl requestPresenter;
    private SelectedImagesAdapter selectedImagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_images);
        ButterKnife.bind(this);

        requestPresenter = new RequestPresenterImpl(this);

        selectedImagesAdapter = new SelectedImagesAdapter(this,
                requestPresenter.getSekectList(),requestPresenter);
        requestGridChoicedImages.setAdapter(selectedImagesAdapter);
    }

    @OnClick({R.id.request_btn_upImages})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.request_btn_upImages:
                requestPresenter.upImages();
                break;
        }
    }

    @Override
    public void onShowChoicedImages(ArrayList<String> imgs) {
        selectedImagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUped2Server(ArrayList<String> url) {
        UIManger.startImageList(this,url);

        //发起请求，将图片的网络地址传给服务器保存

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
