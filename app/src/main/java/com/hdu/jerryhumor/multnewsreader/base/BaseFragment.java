package com.hdu.jerryhumor.multnewsreader.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hanjianhao on 17/8/3.
 */

public abstract class BaseFragment extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getResourceId(), null);
        initView(view);
        initData();
        initEvent();
        return view;

    }

    abstract protected int getResourceId();

    abstract protected void initView(View view);

    abstract protected void initData();

    abstract protected void initEvent();
}
