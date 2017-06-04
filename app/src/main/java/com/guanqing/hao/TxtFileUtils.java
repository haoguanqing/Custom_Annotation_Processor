package com.guanqing.hao;

import android.content.Context;
import android.support.annotation.NonNull;

import com.guanqing.hao.kehan_dict.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TxtFileUtils {

    @NonNull
    public static String readFromDict(@NonNull Context context) {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(
                context.getResources().openRawResource(R.raw.en_ch_dict));
        final BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream));


        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            int length;
            while ((length = bufferedInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            return out.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
