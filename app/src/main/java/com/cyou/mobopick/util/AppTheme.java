package com.cyou.mobopick.util;

import android.graphics.Color;

/**
 * Created by chengfei on 14/10/29.
 */
public class AppTheme {

    private static String[] bgColorStrs = new String[]{"#5a63ad", "#00adce", "#ff9c21", "#4294ef", "#22dabe", "#1094a5", "#f07079", "#f0cc3e", "#40c7db"};


    static {
        setCurBgColorStr(0);
    }
    public static  String getBgColorString(int position) {
        return bgColorStrs[(position % bgColorStrs.length)];
    }

    public static String curBgColorStr;


    public static void setCurBgColorStr(int index) {
        AppTheme.curBgColorStr = getBgColorString(index);
    }

    public static int getCurBgColor() {
        return Color.parseColor(curBgColorStr);
    }
}
