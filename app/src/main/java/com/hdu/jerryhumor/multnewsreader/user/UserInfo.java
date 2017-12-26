package com.hdu.jerryhumor.multnewsreader.user;

/**
 * Created by hanjianhao on 2017/12/18.
 */

public class UserInfo {

    private static UserInfo mInstance;

    private String userName;
    private String userAccount;
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

    public UserInfo setLogin(boolean isLogin){
        this.isLogin = isLogin;
        return this;
    }

    public UserInfo setUserName(String userName){
        this.userName = userName;
        return this;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public UserInfo setUserAccount(String userAccount) {
        this.userAccount = userAccount;
        return this;
    }
}
