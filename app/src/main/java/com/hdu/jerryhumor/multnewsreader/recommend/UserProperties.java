package com.hdu.jerryhumor.multnewsreader.recommend;

import android.content.Context;
import android.content.SharedPreferences;

import com.hdu.jerryhumor.multnewsreader.exchange.Transformer;

import java.util.List;

/**
 * Created by hanjianhao on 2017/12/11.
 */

public class UserProperties {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static final String USER_PROPERTIES = "user_properties";

    public UserProperties(Context context){
        mSharedPreferences = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void addProperties(int type){
        String tag = Transformer.getTypeTag(type);
        int lastValue = getProperties(tag);

        mEditor.putInt(tag, lastValue + 1);
        mEditor.apply();
    }

    /**
     * 根据标签获取用户配置
     * @param tag           参照
     * @return
     */
    public int getProperties(String tag){
        return mSharedPreferences.getInt(tag, 1);
    }

    public int getProperties(int type){
        return getProperties(Transformer.getTypeTag(type));
    }

    /**
     * 获取排序的 用户配置表 顺序从高到低
     * @return
     */
    public List<Integer> getUserPropertiesListSorted(){
        //todo 按照从高到低的获取
        return null;
    }



}
