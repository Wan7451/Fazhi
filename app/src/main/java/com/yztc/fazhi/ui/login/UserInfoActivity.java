package com.yztc.fazhi.ui.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.yztc.fazhi.R;
import com.yztc.fazhi.databinding.ActivityUserInfoBinding;
import com.yztc.fazhi.dialog.LoadingDialog;
import com.yztc.fazhi.loader.ImageLoader;
import com.yztc.fazhi.ui.common.upimages.IUpImagesPresenter;
import com.yztc.fazhi.ui.common.upimages.IUpImagesView;
import com.yztc.fazhi.ui.common.upimages.UpImagesPresenterImpl;
import com.yztc.fazhi.ui.login.bean.UserBean;
import com.yztc.fazhi.ui.common.choiceicon.ChoiceIconPresenterImpl;
import com.yztc.fazhi.ui.common.choiceicon.IChoiceIconPresenter;
import com.yztc.fazhi.ui.common.choiceicon.IChoiceIconView;
import com.yztc.fazhi.ui.login.mvp.IUserInfoView;
import com.yztc.fazhi.ui.login.mvp.UserInfoPresenterImpl;
import com.yztc.fazhi.util.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity
        implements IUserInfoView, IChoiceIconView, IUpImagesView {

    @BindView(R.id.userInfo_img_icon)
    ImageView userInfoImgIcon;
    @BindView(R.id.userInfo_et_sex)
    EditText userInfoEtSex;
    @BindView(R.id.userInfo_et_age)
    EditText userInfoEtAge;

    private UserInfoPresenterImpl userInfoPresenter;
    private LoadingDialog loadingDialog;


    private ActivityUserInfoBinding dataBinding;

    private IChoiceIconPresenter presenter;


    private String upIconPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);

        ButterKnife.bind(this);
        userInfoPresenter = new UserInfoPresenterImpl(this);
        userInfoPresenter.getUserData();
    }

    @OnClick(R.id.userInfo_btn_save)
    public void onClick() {
        String age = dataBinding.userInfoEtAge.getText().toString();
        String sex = dataBinding.userInfoEtSex.getText().toString();

        if (TextUtils.isEmpty(age) || TextUtils.isEmpty(sex)) {
            showError("不能为空");
            return;
        }

        //双向绑定
        //从View中获取View的数据，设置到Bean 上
        UserBean user = dataBinding.getUser();
        user.setAge(Integer.parseInt(age));
        user.setGender(Integer.parseInt(sex));

        if(!TextUtils.isEmpty(upIconPath)){
            user.setIcon(upIconPath);
        }

        userInfoPresenter.updateUser(user);
    }

    @Override
    public void showUserData(UserBean user) {
        dataBinding.setUser(user);
    }

    @Override
    public void showError(String error) {
        ToastUtils.showErrorToast(this, error);
    }

    @Override
    public void showLoading() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter != null)
            presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showCropImage(File file) {
        Uri uri = Uri.fromFile(file);
        //file:// 路径
        ImageLoader.loadIcon(this, uri.toString(), userInfoImgIcon);

        IUpImagesPresenter presenter=new UpImagesPresenterImpl(this,this);
        //上传到图片的服务器
        presenter.upImage(file);
    }

    @OnClick(R.id.userInfo_img_icon)
    public void onIconClick() {
        //点击头像
        presenter = new ChoiceIconPresenterImpl(this, this);
        String[] items = {"打开相册", "打开相机"};
        new AlertDialog.Builder(UserInfoActivity.this)
                .setItems(items,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    presenter.openGallery();
                                } else {
                                    presenter.openCamera();
                                }
                            }
                        }).show();
    }

    @Override
    public void onUpSuccess(String url) {
        upIconPath=url;
        ToastUtils.showToast(this,"上传成功");
    }

    @Override
    public void onUpError(String error) {
        ToastUtils.showToast(this,"上传失败");
    }
}
