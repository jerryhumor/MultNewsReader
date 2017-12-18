package com.hdu.jerryhumor.multnewsreader.login;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.base.BaseActivity;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.login.bean.LoginResponse;
import com.hdu.jerryhumor.multnewsreader.net.NetworkConnector;
import com.hdu.jerryhumor.multnewsreader.net.callback.BaseCallback;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private EditText etUserName, etPassword;
    private Button btnLogin, btnRegister;

    private NetworkConnector mNetworkConnector;

    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        mNetworkConnector = NetworkConnector.getInstance();
    }

    @Override
    protected void initEvent() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                startRegisterActivity();
                break;
            default:break;
        }
    }

    /**
     * 登录
     */
    private void login(){
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        boolean userInfoValid = true;
        if (TextUtils.isEmpty(userName)){
            showToast("用户名不能为空");
            userInfoValid = false;
        }
        if (TextUtils.isEmpty(password)){
            showToast("密码不能为空");
            userInfoValid = false;
        }
        if (userInfoValid){
            mNetworkConnector.login(userName, password, new BaseCallback<LoginResponse>() {
                @Override
                public void onNetworkError(Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("网络错误");
                        }
                    });
                }

                @Override
                public void onFailed(final String error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("登录失败，" + error);
                        }
                    });
                }

                @Override
                public void onSuccess(final LoginResponse data) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setLoginInfo(data);
                            setLoginSuccessResult(data);
                            finish();
                        }
                    });
                }
            });
        }
    }

    /**
     * 开启注册活动
     */
    private void startRegisterActivity(){

    }

    /**
     * 登录信息写入内存
     * @param data
     */
    private void setLoginInfo(LoginResponse data){
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.setUserName(data.getUserName());
    }

    /**
     * 设置登录结果
     * @param data
     */
    private void setLoginSuccessResult(LoginResponse data){
        Intent intent = new Intent();
        intent.putExtra(IntentExtra.USER_NAME, data.getUserName());
        setResult(RESULT_OK);
    }




}

