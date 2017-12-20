package com.hdu.jerryhumor.multnewsreader.user.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jerryhumor on 2017/12/19.
 */

public class RegisterResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("error")
    private String error;
    @SerializedName("account")
    private String account;
    @SerializedName("user_name")
    private String userName;


    public boolean isSuccess(){
        return "ok".equals(status);
    }

    public String getError(){
        return error;
    }

    public String getAccount() {
        return account;
    }

    public String getUserName() {
        return userName;
    }
}
