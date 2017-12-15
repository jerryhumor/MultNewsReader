package com.hdu.jerryhumor.multnewsreader.recommend;

import android.content.Context;
import android.content.SharedPreferences;

import com.hdu.jerryhumor.multnewsreader.constant.NewsType;
import com.hdu.jerryhumor.multnewsreader.constant.PropertiesTag;
import com.hdu.jerryhumor.multnewsreader.exchange.Transformer;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * 根据类型代号添加用户喜好
     * @param type
     */
    public void addProperties(int type){
        String tag = Transformer.getTypeTag(type);
        addProperties(tag);
    }

    /**
     * 根据标签添加用户喜好
     * @param tag
     */
    public void addProperties(String tag){
        int lastValue = getProperties(tag);
        mEditor.putInt(tag, lastValue + 1);
        mEditor.apply();
    }


    /**
     * 根据标签获取用户配置
     * @param tag           参照 PropertiesTag
     * @return
     */
    public int getProperties(String tag){
        return mSharedPreferences.getInt(tag, 1);
    }

    /**
     * 根据类型代号获取用户配置
     * @param type
     * @return
     */
    public int getProperties(int type){
        return getProperties(Transformer.getTypeTag(type));
    }

    /**
     * 获取排序的 用户配置表 顺序从高到低
     * @return
     */
    public List<Integer> getUserPropertiesListSorted(){
        List<Favour> propertyList = new ArrayList<>();
        List<Integer> sortedPropertyList = new ArrayList<>();
        propertyList.add(new Favour(getProperties(PropertiesTag.ART), NewsType.CODE_ART));
        propertyList.add(new Favour(getProperties(PropertiesTag.CAR), NewsType.CODE_CAR));
        propertyList.add(new Favour(getProperties(PropertiesTag.EDUCATE), NewsType.CODE_EDUCATE));
        propertyList.add(new Favour(getProperties(PropertiesTag.FINANCE), NewsType.CODE_FINANCE));
        propertyList.add(new Favour(getProperties(PropertiesTag.FUN), NewsType.CODE_FUN));
        propertyList.add(new Favour(getProperties(PropertiesTag.POLITICS), NewsType.CODE_POLITICS));
        propertyList.add(new Favour(getProperties(PropertiesTag.SOCIAL), NewsType.CODE_SOCIAL));
        propertyList.add(new Favour(getProperties(PropertiesTag.SPORT), NewsType.CODE_SPORT));
        propertyList.add(new Favour(getProperties(PropertiesTag.TECH), NewsType.CODE_TECH));
        propertyList.add(new Favour(getProperties(PropertiesTag.WAR), NewsType.CODE_WAR));
        Collections.sort(propertyList);
        for (int i = propertyList.size() - 1; i >=0; i--){
            sortedPropertyList.add(propertyList.get(i).getType());
        }
        return sortedPropertyList;
    }



}
