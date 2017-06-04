package com.guanqing.hao;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.samples.vision.ocrreader.R;

import java.io.InputStream;

public class TxtFileUtils {
    public static void read(@NonNull Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.en_ch_dict);
    }
}
