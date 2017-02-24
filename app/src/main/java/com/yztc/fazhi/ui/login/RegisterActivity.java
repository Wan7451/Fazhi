package com.yztc.fazhi.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yztc.fazhi.R;
import com.yztc.fazhi.base.BaseActivity;
import com.yztc.fazhi.base.from.FzButton;
import com.yztc.fazhi.base.from.FzEditText;
import com.yztc.fazhi.dialog.LoadingDialog;
import com.yztc.fazhi.ui.login.mvp.ILoginPresenter;
import com.yztc.fazhi.ui.login.mvp.ILoginView;
import com.yztc.fazhi.ui.login.mvp.LoginPresenterImpl;
import com.yztc.fazhi.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.register_et_phone)
    FzEditText registerEtPhone;
    @BindView(R.id.register_et_pasd)
    FzEditText registerEtPasd;
    @BindView(R.id.register_et_ver)
    FzEditText registerEtVer;
    @BindView(R.id.register_btn_ver)
    FzButton registerBtnVer;
    @BindView(R.id.register_btn_register)
    FzButton registerBtnRegister;

    private ILoginPresenter presenter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter=new LoginPresenterImpl(this);
    }

    @OnClick({R.id.register_btn_ver, R.id.register_btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn_ver:
                //获取验证码
                presenter.getCaptchaCode((Button) view,registerEtPhone.getText().toString());
                break;
            case R.id.register_btn_register:
                //注册
                String phone = registerEtPhone.getText().toString();
                String pasd = registerEtPasd.getText().toString();
                String ver = registerEtVer.getText().toString();
                presenter.register(phone,pasd,ver);
                break;
        }
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
        loadingDialog.hide();
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void showHint(String hint) {
        ToastUtils.showToast(this,hint);
    }
}
