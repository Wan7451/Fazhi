package com.yztc.fazhi.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.yztc.fazhi.R;
import com.yztc.fazhi.dialog.LoadingDialog;
import com.yztc.fazhi.ui.login.mvp.ILoginPresenter;
import com.yztc.fazhi.ui.login.mvp.ILoginView;
import com.yztc.fazhi.ui.login.mvp.LoginPresenterImpl;
import com.yztc.fazhi.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.login_et_phone)
    EditText loginEtPhone;
    @BindView(R.id.login_et_pasd)
    EditText loginEtPasd;
    private LoginPresenterImpl loginPresenter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this);
    }


    @OnClick(R.id.login_btn_login)
    public void onClick() {
        loginPresenter.login(loginEtPhone.getText().toString(),
                loginEtPasd.getText().toString());
    }

    @Override
    public void showHint(String hint) {
        ToastUtils.showToast(this,hint);
    }

    @Override
    public void showError(String error) {
        ToastUtils.showErrorToast(this,error);
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

}
