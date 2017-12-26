package com.hdu.jerryhumor.multnewsreader.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hanjianhao on 17/7/28.
 */

public class SharedPreferencesUtil {

    private static SharedPreferencesUtil mUtil;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static final String TAG_PRE_NAME = "user_data";
    private static final String TAG_REMEMBER = "remember_password";
    private static final String TAG_USER_NAME = "user_name";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_IS_LOGIN = "is_login";
    private static final String TAG_USER_ACCOUNT = "user_account";


    private SharedPreferencesUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(TAG_PRE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        if (null == mUtil) {
            mUtil = new SharedPreferencesUtil(context);
        }
        return mUtil;
    }

    public void writeRememberPassword(boolean rememberPassword) {
        mEditor.putBoolean(TAG_REMEMBER, rememberPassword);
        mEditor.apply();
    }

    public void writeUserName(String userName) {
        mEditor.putString(TAG_USER_NAME, userName);
        mEditor.apply();
    }

    public void writePassword(String password) {
        mEditor.putString(TAG_PASSWORD, password);
        mEditor.apply();
    }

    public void writeLogin(boolean isLogin){
        mEditor.putBoolean(TAG_IS_LOGIN, isLogin);
        mEditor.apply();
    }

    public void writeUserAccount(String userAccount){
        mEditor.putString(TAG_USER_ACCOUNT, userAccount);
        mEditor.apply();
    }


    public String getUserName() {
        return mSharedPreferences.getString(TAG_USER_NAME, "");
    }

    public String getUserAccount(){
        return mSharedPreferences.getString(TAG_USER_ACCOUNT, "");
    }

    public String getPassword() {
        return mSharedPreferences.getString(TAG_PASSWORD, "");
    }

    public boolean isRemember() {
        return mSharedPreferences.getBoolean(TAG_REMEMBER, false);
    }

    public boolean isLogin(){
        return mSharedPreferences.getBoolean(TAG_IS_LOGIN, false);
    }
}
