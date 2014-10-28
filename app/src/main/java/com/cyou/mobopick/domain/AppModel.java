package com.cyou.mobopick.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.R;
import com.cyou.mobopick.util.CalenderUtils;
import com.cyou.mobopick.util.Constant;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by chengfei on 14-10-6.
 */
public class AppModel implements Parcelable {

    public String appSize;
    public String authorAvatarUrl;
    public String authorCareer;
    public int authorId;
    public String authorName;
    public int commentTimes;
    @SerializedName(value = "description")
    public String content;
    @SerializedName(value = "image")
    public String coverImageUrl;
    @SerializedName(value = "post_time")
    public String createdTime;
    public String digest;
    @SerializedName(value = "thumb")
    public String iconUrl;
    @SerializedName(value = "id")
    public int id;
    public boolean isFavored;
    public int minSdkVer;
    public String packageName;
    public int showTimes;
    public String subTitle;
    @SerializedName(value = "title")
    public String title;
    public int upTimes;
    public String updatedTime;
    public int upNum;
    @SerializedName(value = "url")
    public String downloadUrl;
    @SerializedName(value = "tags")
    public String tags;

    public String views;

    public String favorites;

    @SerializedName(value = "json_data")
    public String imageTextJson;
    private List<ImageText> imageText;

    public String creative;
    public String usability;
    @SerializedName(value = "interface")
    public String interface_;
    public String smooth;
    public String functionality;
    public String support;
    public String oppose;
    public String filesize;
    public String size;



    public AppModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appSize);
        dest.writeString(this.authorAvatarUrl);
        dest.writeString(this.authorCareer);
        dest.writeInt(this.authorId);
        dest.writeString(this.authorName);
        dest.writeInt(this.commentTimes);
        dest.writeString(this.content);
        dest.writeString(this.coverImageUrl);
        dest.writeString(this.createdTime);
        dest.writeString(this.digest);
        dest.writeString(this.iconUrl);
        dest.writeInt(this.id);
        dest.writeByte(isFavored ? (byte) 1 : (byte) 0);
        dest.writeInt(this.minSdkVer);
        dest.writeString(this.packageName);
        dest.writeInt(this.showTimes);
        dest.writeString(this.subTitle);
        dest.writeString(this.title);
        dest.writeInt(this.upTimes);
        dest.writeString(this.updatedTime);
        dest.writeInt(this.upNum);
    }

    private AppModel(Parcel in) {
        this.appSize = in.readString();
        this.authorAvatarUrl = in.readString();
        this.authorCareer = in.readString();
        this.authorId = in.readInt();
        this.authorName = in.readString();
        this.commentTimes = in.readInt();
        this.content = in.readString();
        this.coverImageUrl = in.readString();
        this.createdTime = in.readString();
        this.digest = in.readString();
        this.iconUrl = in.readString();
        this.id = in.readInt();
        this.isFavored = in.readByte() != 0;
        this.minSdkVer = in.readInt();
        this.packageName = in.readString();
        this.showTimes = in.readInt();
        this.subTitle = in.readString();
        this.title = in.readString();
        this.upTimes = in.readInt();
        this.updatedTime = in.readString();
        this.upNum = in.readInt();
    }

    public static final Creator<AppModel> CREATOR = new Creator<AppModel>() {
        public AppModel createFromParcel(Parcel source){
            return new AppModel(source);
        }

        public AppModel[] newArray(int size) {
            return new AppModel[size];
        }
    };

    public String getIconUrl() {
        return new StringBuilder(Constant.getBaseUrl()).append(iconUrl).toString();
    }

    public String getCoverImageUrl() {
        return new StringBuilder(Constant.getBaseUrl()).append(coverImageUrl).toString();
    }


    public Calendar getCreateCalender() {
//        2014-09-24 11:04:19
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(createdTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCreateDay() {
        Calendar calendar = getCreateCalender();
        if (calendar == null) {
            return null;
        }
        Calendar today = Calendar.getInstance();
        if (CalenderUtils.isSameYear(calendar, today)) {
            if (CalenderUtils.isSameDay(calendar, today)) {
                return MyApplication.getInstance().getResources().getString(R.string.today);
            }
            Calendar yesterday = (Calendar) today.clone();
            yesterday.add(Calendar.DAY_OF_YEAR, -1);
            if (CalenderUtils.isSameDay(calendar, yesterday)) {
                return MyApplication.getInstance().getResources().getString(R.string.yesterday);
            }

        }
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }


    public String getCreateMonthWeek() {
        StringBuilder builder = new StringBuilder();
        Calendar calendar = getCreateCalender();
        if (calendar == null) {
            return null;
        }
        if (isTodayOrYesterday()) {
            return "";
        }
        builder.append(calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, MyApplication.getLocal()));
        builder.append("\n");
        builder.append(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, MyApplication.getLocal()));
        return builder.toString();
    }


    /**
     * 判断创建时间是否是今天或昨天
     * @return
     */
    public boolean isTodayOrYesterday() {
        Calendar calendar = getCreateCalender();
        if (calendar == null) {
            return false;
        }
        Calendar today = Calendar.getInstance();
        if (CalenderUtils.isSameYear(calendar, today)) {
            if (CalenderUtils.isSameDay(calendar, today)) {
                return true;
            }
            Calendar yesterday = (Calendar) today.clone();
            yesterday.add(Calendar.DAY_OF_YEAR, -1);
            if (CalenderUtils.isSameDay(calendar, yesterday)) {
                return true;
            }
        }
        return false;
    }

    public List<ImageText> getImageText() {
        if (TextUtils.isEmpty(imageTextJson)) {
            return null;
        }
        if (imageText != null) {
            return imageText;
        }

        return imageText;
    }
}
