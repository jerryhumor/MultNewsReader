package com.hdu.jerryhumor.multnewsreader.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by hanjianhao on 17/8/2.
 * 使用时请注意实现顺序 控件->数据->事件
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static Toast toast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());

        initView();
        initData();
        initEvent();
    }

    //获取布局文件
    abstract protected int getResourceId();

    //初始化控件
    abstract protected void initView();

    //初始化数据
    abstract protected void initData();

    //初始化事件，给控件设置数据 adapter等等
    abstract protected void initEvent();

    //显示toast通知
    public void showToast(String content){
        if(toast == null){
            toast = Toast.makeText(BaseActivity.this, content, Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
    }
}
