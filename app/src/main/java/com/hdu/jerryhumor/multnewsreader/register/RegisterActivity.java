package com.hdu.jerryhumor.multnewsreader.register;

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
import com.hdu.jerryhumor.multnewsreader.register.bean.RegisterResponse;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private EditText etUserName, etPassword;
    private Button btnRegister;

    private NetworkConnector mNetworkConnector;

    @Override
    protected int getResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
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
            mNetworkConnector.register(userName, password, new BaseCallback<RegisterResponse>() {
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
                public void onSuccess(final RegisterResponse data) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册成功");
                            setActivityResult(data.getUserName());
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
    private void setActivityResult(String userName){
        setResult(RESULT_OK);
        Intent intent = new Intent();
        intent.putExtra(IntentExtra.USER_NAME, userName);
    }
}
