package com.guanqing.hao;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import io.reactivex.annotations.NonNull;

public class Dict {
    private final SimpleArrayMap<String, String> mDict;

    public Dict(@NonNull Context context) {
        mDict = IoUtils.getDict(context);
    }

    public SimpleArrayMap<String, String> get() {
        return mDict;
    }
}
