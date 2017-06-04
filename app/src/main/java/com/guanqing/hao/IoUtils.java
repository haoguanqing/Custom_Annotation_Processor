package com.guanqing.hao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import com.guanqing.hao.kehan_dict.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class IoUtils {

    @NonNull
    public static Single<SimpleArrayMap<String, String>> getDictObservable(@NonNull final Context context) {
        return Single
                .just(getDict(context))
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    public static SimpleArrayMap<String, String> getDict(@NonNull final Context context) {
        return createMapFromString(readFromDict(context));
    }

    @NonNull
    private static String readFromDict(@NonNull Context context) {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(
                context.getResources().openRawResource(R.raw.en_ch_dict));
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

    @NonNull
    private static SimpleArrayMap<String, String> createMapFromString(@NonNull String s) {
        final SimpleArrayMap<String, String> dict = new ArrayMap<>();
        final int len = s.length();
        int start = 0;
        int end = findKeyEnd(s, start);
        while (start < s.length()) {
            final int[] nextKey = findNextKey(s, end, len);
            String value = s.substring(end, nextKey[0]).trim();
            value = value.replace("\\n", "\r\n");
            dict.put(s.substring(start, end).trim(), value);
            start = nextKey[0];
            end = nextKey[1];
        }
        return dict;
    }

    private static int findKeyEnd(String s, int start) {
        int end = start;
        while (end < s.length()) {
            if (s.charAt(end) == '\t') {
                return end;
            }
            end++;
        }
        return end;
    }

    private static int[] findNextKey(String s, int end, int len) {
        if (end >= len) {
            return new int[]{len, len};
        }
        int nextStart = end + 1;
        while (nextStart < s.length()) {
            if (s.charAt(nextStart - 1) == '\n' &&
                    s.charAt(nextStart - 2) == '\r') {
                return new int[]{nextStart, findKeyEnd(s, nextStart)};
            }
            nextStart++;
        }
        return new int[]{len, len};
    }

    private static int findKeyStart(String s, int end) {
        int start = end - 1;
        while (start > 0) {
            if (s.charAt(start - 1) == '\n') {
                return start;
            }
            start--;
        }
        return 0;
    }
}
