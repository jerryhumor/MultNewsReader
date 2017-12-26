package com.hdu.jerryhumor.multnewsreader;

import android.app.Application;

import com.hdu.jerryhumor.multnewsreader.user.UserInfo;
import com.hdu.jerryhumor.multnewsreader.util.SharedPreferencesUtil;

/**
 * Created by hanjianhao on 2017/12/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(this);
        if (util.isLogin()){
            UserInfo.getInstance()
                    .setLogin(true)
                    .setUserName(util.getUserName())
                    .setUserAccount(util.getUserAccount());
        }
    }
}
