package com.cyou.mobopick.bus;

/**
 * Created by chengfei on 14/11/7.
 */
public class AppInstallEvent {
    private String packageName;

    public AppInstallEvent(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }
}
