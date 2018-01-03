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
import com.hdu.jerryhumor.multnewsreader.net.NetworkConnector;
import com.hdu.jerryhumor.multnewsreader.base.BaseCallback;
import com.hdu.jerryhumor.multnewsreader.user.bean.RegisterResponse;
import com.hdu.jerryhumor.multnewsreader.util.JLog;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private EditText etUserName, etPassword, etAccount, etPasswordRepeat;
    private Button btnRegister;

    private NetworkConnector mNetworkConnector;

    @Override
    protected int getResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        etAccount = findViewById(R.id.et_account);
        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
        etPasswordRepeat = findViewById(R.id.et_password_repeat);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        mNetworkConnector = NetworkConnector.getInstance();
    }

    @Override
    protected void initEvent() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                register();
                break;
            default:break;
        }
    }

    /**
     * 注册
     */
    private void register(){
        String account = etAccount.getText().toString();
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String passwordRepeat = etPasswordRepeat.getText().toString();
        boolean userInfoValid = true;
        if (TextUtils.isEmpty(userName)){
            showToast("用户名不能为空");
            userInfoValid = false;
        }
        if (userInfoValid && TextUtils.isEmpty(password)){
            showToast("密码不能为空");
            userInfoValid = false;
        }
        if (userInfoValid && TextUtils.isEmpty(passwordRepeat)){
            showToast("请填写重复密码");
            userInfoValid = false;
        }
        if (userInfoValid && !password.equals(passwordRepeat)){
            showToast("两次密码不相同");
            userInfoValid = false;
        }

        if (userInfoValid){
            mNetworkConnector.register(account, userName, password, new BaseCallback<RegisterResponse>() {
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
                            showToast("注册失败，" + error);
                        }
                    });
                }

                @Override
                public void onSuccess(final RegisterResponse data) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册成功");
                            setActivityResult(data.getUserName(), data.getAccount());
                            finish();
                        }
                    });
                }
            });
        }
    }

    /**
     * 返回结果
     */
    private void setActivityResult(final String userName, final String account){
        Intent intent = new Intent();
        intent.putExtra(IntentExtra.USER_NAME, userName);
        intent.putExtra(IntentExtra.USER_ACCOUNT, account);
        setResult(RESULT_OK, intent);
    }
}
