package com.cyou.mobopick.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.R;
import com.cyou.mobopick.util.AppTheme;
import com.cyou.mobopick.util.CalenderUtils;
import com.cyou.mobopick.util.Constant;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @SerializedName(value = "start_time")
    public String startTime;
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

    @SerializedName(value = "json_data")
    public String imageTextJson;
    private List<ImageText> imageText;


    @SerializedName(value = "filesize")
    public String size;


    /**
     * 评论对应表情数量
     */
    @SerializedName(value = "creative")
    public int accepted;
    @SerializedName(value = "usability")
    public int joy;
    @SerializedName(value = "interface")
    public int surprised;
    @SerializedName(value = "smooth")
    public int rejected;
    @SerializedName(value = "functionality")
    public int fearful;

    public int themeColor;


    public AppModel() {
    }

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
            String base = TextUtils.isEmpty(startTime) ? createdTime : startTime;
            Date date = format.parse(base);
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


    public String getSizeAndViews() {
        StringBuilder builder = new StringBuilder();
        builder.append(size).append(" | ").append(MyApplication.getInstance().getString(R.string.views_total, views)).toString();
        return builder.toString();
    }

    public List<EmojiComment> getEmobjiComment() {
        List<EmojiComment> comments = new ArrayList<EmojiComment>();
        comments.add(new EmojiComment(R.string.emoji_accepted, R.drawable.emoji_comment_accepted, accepted, "creative"));
        comments.add(new EmojiComment(R.string.emoji_joy, R.drawable.emoji_comment_joy, joy, "usability"));
        comments.add(new EmojiComment(R.string.emoji_surprised, R.drawable.emoji_comment_surprised, surprised, "interface"));
        comments.add(new EmojiComment(R.string.emoji_rejected, R.drawable.emoji_comment_rejected, rejected, "smooth"));
        comments.add(new EmojiComment(R.string.emoji_fearful, R.drawable.emoji_comment_fearul, fearful, "functionality"));
        return comments;
    }

    public class EmojiComment {

        public int label;
        public int drawable;
        public int totalNum;
        public String interfaceName;

        public EmojiComment(int label, int drawable, int totalNum, String interfaceName) {
            this.label = label;
            this.drawable = drawable;
            this.totalNum = totalNum;
            this.interfaceName = interfaceName;
        }
    }

    public List<ImageText> getImageTexts() {
        List<ImageText> imageTexts = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(imageTextJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray itemArrays = jsonArray.getJSONArray(i);
                String imageUrl = itemArrays.getString(0);
                String text = itemArrays.getString(1);
                imageTexts.add(new ImageText(text, imageUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageTexts;
    }

    public int getThemeColor() {
        if (themeColor == 0) {
            themeColor = AppTheme.getCurBgColor();
        }
        return themeColor;
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
        dest.writeString(this.startTime);
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
        dest.writeString(this.downloadUrl);
        dest.writeString(this.tags);
        dest.writeString(this.views);
        dest.writeString(this.imageTextJson);
        dest.writeList(imageText);
        dest.writeString(this.size);
        dest.writeInt(this.accepted);
        dest.writeInt(this.joy);
        dest.writeInt(this.surprised);
        dest.writeInt(this.rejected);
        dest.writeInt(this.fearful);
        dest.writeInt(this.themeColor);
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
        this.startTime = in.readString();
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
        this.downloadUrl = in.readString();
        this.tags = in.readString();
        this.views = in.readString();
        this.imageTextJson = in.readString();
        this.imageText = in.readArrayList(ClassLoader.getSystemClassLoader());
        this.size = in.readString();
        this.accepted = in.readInt();
        this.joy = in.readInt();
        this.surprised = in.readInt();
        this.rejected = in.readInt();
        this.fearful = in.readInt();
        this.themeColor = in.readInt();
    }

    public static final Creator<AppModel> CREATOR = new Creator<AppModel>() {
        public AppModel createFromParcel(Parcel source) {
            return new AppModel(source);
        }

        public AppModel[] newArray(int size) {
            return new AppModel[size];
        }
    };
}
