package com.hdu.jerryhumor.multnewsreader.user;

/**
 * Created by hanjianhao on 2017/12/18.
 */

public class UserInfo {

    private static UserInfo mInstance;

    private String userName;
    private boolean isLogin;

    private UserInfo(){}

    public static UserInfo getInstance(){
        if (mInstance == null){
            mInstance = new UserInfo();
        }
        return mInstance;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isLogin(){
        return isLogin;
    }

    public void setUserName(String userName){
        this.userName = userName;
        isLogin = true;
    }
}
