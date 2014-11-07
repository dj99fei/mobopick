package com.cyou.mobopick.util;

import android.content.Intent;
import android.widget.Toast;

import com.cyou.mobopick.MyApplication;

/**
 * Created by chengfei on 14/11/5.
 */
public class Utils {

    public static void startAPP(String appPackageName){
        try{
            Intent intent = MyApplication.getInstance().getPackageManager().getLaunchIntentForPackage(appPackageName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getInstance().startActivity(intent);
        }catch(Exception e){
            Toast.makeText(MyApplication.getInstance(), "没有安装", Toast.LENGTH_LONG).show();
        }
    }
}
