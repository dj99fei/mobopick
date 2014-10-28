package com.cyou.mobopick.util;

import android.util.Log;

import com.cyou.mobopick.BuildConfig;

/**
 * Created by chengfei on 14-10-14.
 */
public class LogUtils {

    public static final void e(String tag, Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, e.getMessage());
        }
    }
    public static final void d(String tag, String format, Object ... params) {
        if (BuildConfig.DEBUG) {
            if (params != null && params.length > 0) {
                Log.d(tag, String.format(format, params));
            } else {
                Log.d(tag, format);
            }
        }
    }
}
