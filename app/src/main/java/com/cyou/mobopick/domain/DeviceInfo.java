package com.cyou.mobopick.domain;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.R;
import com.cyou.mobopick.util.LogUtils;
import com.cyou.mobopick.util.SharedPreferencesHelper;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by chengfei on 14-10-22.
 */
public class DeviceInfo {

    private static final String TAG = DeviceInfo.class.getSimpleName();

    static {
        MyApplication context = MyApplication.getInstance();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        mac = getMacAdress();
        ip = getIpAddress();
        version = getVersionCode();
        imei = tm.getDeviceId();
        isp = tm.getSimOperatorName();
        country = tm.getNetworkCountryIso();
        ua = Build.MODEL;
        apps = getInstalledApps();
        uuid = SharedPreferencesHelper.getInstance().withKey(R.string.key_uuid).get(int.class, 0);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        density = metrics.density;
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
        densityDpi = metrics.densityDpi;
    }

    public static String mac;
    public static String ua;
    public static String imei;
    public static String apps;
    public static String isp;
    public static String version;
    public static String ip;
    public static String lbs;
    public static String country;
    public static int uuid;

    public static float density;
    public static int screenWidth;
    public static int screenHeight;
    public static int densityDpi;



    public static String getMacAdress() {
        WifiManager wifi = (WifiManager) MyApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getVersionCode() {
        Context context = MyApplication.getInstance();
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(TAG, e);
        }
        return "0";
    }

    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            LogUtils.e(TAG, ex);
        }
        return "";
    }

    public static String getInstalledApps() {
        Context context = MyApplication.getInstance();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        StringBuilder packagesBuilder = new StringBuilder();
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (p.versionName == null) {
                continue;
            }
            //只上传非系统应用（用户自主安装的）
            if((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                try {
                    packagesBuilder.append(pm.getApplicationInfo(p.packageName, 0).packageName)
                        .append(",");
                } catch (PackageManager.NameNotFoundException e) {
                    LogUtils.d(TAG, "Package not found: %s" , p.packageName);
                }
            }
        }
        if (packagesBuilder.length() > 0) {
            packagesBuilder.deleteCharAt(packagesBuilder.lastIndexOf(","));
        }
        return packagesBuilder.toString();
    }


    public static void addPackage(String packageName) {
        apps = new StringBuilder(apps).append(",").append(packageName).toString();
    }

    public static void refreshApps() {
        apps = getInstalledApps();
    }

}
