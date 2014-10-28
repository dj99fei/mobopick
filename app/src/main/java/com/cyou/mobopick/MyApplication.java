package com.cyou.mobopick;

import android.app.Application;

import com.cyou.mobopick.util.CheckUpdateHelper;
import com.cyou.mobopick.util.UploadUserInfoHelper;
import com.cyou.mobopick.volley.MyVolley;

import java.util.Locale;

public class MyApplication extends Application {

    private static MyApplication myApp;
    /**
     * app 版本信息
     */


    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        MyVolley.init(this);

        CheckUpdateHelper.getInstance().check();
        UploadUserInfoHelper.getInstance().upload();
    }



    public static MyApplication getInstance() {
        return myApp;
    }

    public static Locale getLocal() {
        return myApp.getResources().getConfiguration().locale;
    }
}
