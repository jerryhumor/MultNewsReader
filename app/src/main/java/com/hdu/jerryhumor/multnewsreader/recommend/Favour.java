package com.hdu.jerryhumor.multnewsreader.recommend;

import android.support.annotation.NonNull;

/**
 * Created by jerryhumor on 2017/12/15.
 */

public class Favour implements Comparable<Favour>{

    private int type;                       //类型代号
    private int frequency;                  //频率

    public Favour(int type, int frequency) {
        this.type = type;
        this.frequency = frequency;
    }

    public int getType() {
        return type;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public int compareTo(@NonNull Favour favour) {
        return this.frequency - favour.getFrequency();
    }
}
