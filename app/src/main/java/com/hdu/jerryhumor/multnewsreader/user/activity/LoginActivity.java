package com.hdu.jerryhumor.multnewsreader.user.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.base.BaseActivity;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.user.UserInfo;
import com.hdu.jerryhumor.multnewsreader.user.bean.LoginResponse;
import com.hdu.jerryhumor.multnewsreader.net.NetworkConnector;
import com.hdu.jerryhumor.multnewsreader.base.BaseCallback;
import com.hdu.jerryhumor.multnewsreader.util.SharedPreferencesUtil;
import com.igexin.sdk.PushManager;

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
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentExtra.REGISTER_ACTIVITY && resultCode == RESULT_OK){
            String userName = data.getStringExtra(IntentExtra.USER_NAME);
            String userAccount = data.getStringExtra(IntentExtra.USER_ACCOUNT);
            PushManager.getInstance().bindAlias(LoginActivity.this, userAccount);
            setLoginInfo(userName, userAccount);
            setLoginSuccessResult(userName);
            finish();
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
                            setLoginInfo(data.getUserName(), data.getAccount());
                            setLoginSuccessResult(data.getUserName());
                            PushManager.getInstance().bindAlias(LoginActivity.this, data.getAccount());
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
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, IntentExtra.REGISTER_ACTIVITY);
    }

    /**
     * 登录信息写入内存
     * @param userName
     */
    private void setLoginInfo(String userName, String userAccount){
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.setLogin(true)
                .setUserName(userName)
                .setUserAccount(userAccount);
        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(LoginActivity.this);
        util.writeLogin(true);
        util.writeUserName(userName);
        util.writeUserAccount(userAccount);
    }

    /**
     * 设置登录结果
     * @param userName
     */
    private void setLoginSuccessResult(final String userName){
        Intent intent = new Intent();
        intent.putExtra(IntentExtra.USER_NAME, userName);
        setResult(RESULT_OK, intent);
    }
}

