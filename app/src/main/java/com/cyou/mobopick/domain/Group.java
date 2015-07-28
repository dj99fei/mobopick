package com.cyou.mobopick.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chengfei on 14/11/11.
 */
public class Group implements Parcelable {

    @SerializedName(value = "group_name")
    public String groupName;
    @SerializedName(value = "group_id")
    public String groupId;
    @SerializedName(value = "group_description")
    public String description;
    public int apkNum;
    public int memeberNum;
    /**
     * 我在这个组内匹配的apk数量
     */
    public int myApkNumInGroup;

    public int matchedRate;


    public List<AppModel> appModels;

    public Group(String groupId) {
        this.groupId = groupId;
    }

    public Group() {
    }

    public Group(String groupName, String description) {
        this.groupName = groupName;
        this.description = description;
    }

    public Group cloneGroup() {
        Group newGroup = new Group();
        newGroup.matchedRate = matchedRate;
        newGroup.myApkNumInGroup = myApkNumInGroup;
        newGroup.memeberNum = memeberNum;
        newGroup.description = description;
        newGroup.groupName = groupName;
        newGroup.apkNum = apkNum;
        newGroup.groupId = groupId;
        return newGroup;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupName);
        dest.writeString(this.groupId);
        dest.writeString(this.description);
        dest.writeInt(this.apkNum);
        dest.writeInt(this.memeberNum);
        dest.writeInt(this.myApkNumInGroup);
        dest.writeInt(this.matchedRate);
//        dest.writeTypedList(appModels);
        dest.writeList(appModels);
    }

    private Group(Parcel in) {
        this.groupName = in.readString();
        this.groupId = in.readString();
        this.description = in.readString();
        this.apkNum = in.readInt();
        this.memeberNum = in.readInt();
        this.myApkNumInGroup = in.readInt();
        this.matchedRate = in.readInt();
//        in.readTypedList(appModels, AppModel.CREATOR);
        this.appModels = in.readArrayList(ClassLoader.getSystemClassLoader());
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public int getMatchedRate() {
        return (int) (myApkNumInGroup / Float.valueOf(apkNum) * 100);
    }
}
