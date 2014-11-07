package com.cyou.mobopick.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cyou.mobopick.bus.AppInstallEvent;
import com.cyou.mobopick.domain.DeviceInfo;

import de.greenrobot.event.EventBus;

/**
 * Created by chengfei on 14/11/7.
 */
public class AppInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getDataString();
//        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {     // install
//            DeviceInfo.addPackage(packageName);
//
//        }
//
//        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {   // uninstall
//            DeviceInfo.refreshApps();
//        }
        DeviceInfo.refreshApps();
        EventBus.getDefault().post(new AppInstallEvent(packageName));
    }
}
