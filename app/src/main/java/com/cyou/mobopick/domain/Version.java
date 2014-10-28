package com.cyou.mobopick.domain;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;

import com.cyou.mobopick.MyApplication;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Created by chengfei on 14-10-21.
 */
public class Version implements Comparator<Version>, Parcelable {

    @SerializedName(value = "version_name")
    public String versionName;
    @SerializedName(value = "version_code")
    public int versionCode;
    @SerializedName(value = "description")
    public String description;
    @SerializedName(value = "download_url")
    public String downloadUrl;

    private static boolean sessionNotified;

    /**
     * 本地安装的版本
     */
    private static Version version;
    /**
     * 线上最新版本
     */
    private static Version lastestVersion;

    static {
        initVersion();
    }

    @Override
    public int compare(Version lhs, Version rhs) {
        return Integer.compare(lhs.versionCode, rhs.versionCode);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.versionName);
        dest.writeInt(this.versionCode);
        dest.writeString(this.description);
        dest.writeString(this.downloadUrl);
    }

    public Version() {
    }

    private Version(Parcel in) {
        this.versionName = in.readString();
        this.versionCode = in.readInt();
        this.description = in.readString();
        this.downloadUrl = in.readString();
    }

    public static final Creator<Version> CREATOR = new Creator<Version>() {
        public Version createFromParcel(Parcel source) {
            return new Version(source);
        }

        public Version[] newArray(int size) {
            return new Version[size];
        }
    };

    private static void initVersion() {
        version = new Version();
        Context context = MyApplication.getInstance();
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version.versionName = pi.versionName;
            version.versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Version getLastestVersion() {
        return lastestVersion;
    }

    public static void setLastestVersion(Version lastestVersion) {
        Version.lastestVersion = lastestVersion;
    }

    public static Version getVersion() {
        return version;
    }

    public static boolean needToNotify() {
        if (lastestVersion == null) {
            return false;
        }
        boolean isNeed = lastestVersion.versionCode > Version.getVersion().versionCode;
//        isNeed &= !(SharedPreferencesHelper.getInstance().withKey(R.string.key_poped_version_code).get(int.class, -1) > Version.getVersion().versionCode);
        isNeed &= !sessionNotified;
        return isNeed;
    }

    public static void setSessionNotified(boolean sessionNotified) {
        Version.sessionNotified = sessionNotified;
    }
}
