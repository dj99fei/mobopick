package com.cyou.mobopick.domain;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.R;
import com.cyou.mobopick.bus.DownloadStartEvent;
import com.cyou.mobopick.providers.DownloadManager;
import com.cyou.mobopick.providers.downloads.Downloads;
import com.cyou.mobopick.util.AppTheme;
import com.cyou.mobopick.util.CalenderUtils;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.util.LogUtils;
import com.cyou.mobopick.util.Utils;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by chengfei on 14-10-6.
 */
public class AppModel implements Parcelable {

    private static final String TAG = AppModel.class.getSimpleName();
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

    public int groupMemberNum;

    public int installedMemberNum;


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

    @SerializedName(value = "package_name")
    public String packageName;
    @SerializedName(value = "package_md5")
    public String packageMd5;


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

    private Uri localUri;
    private String mimeType;
    private int status;
    private boolean queryed;

    public void query() {
        DownloadManager downloadManager = new DownloadManager(MyApplication.getInstance().getContentResolver(), MyApplication.getInstance().getPackageName());
        downloadManager.setAccessAllDownloads(true);
        DownloadManager.Query baseQuery = new DownloadManager.Query().setOnlyIncludeVisibleInDownloadsUi(true);
        Cursor cursor = downloadManager.query(baseQuery);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (downloadUrl != null && downloadUrl.equals(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI)))) {
                    status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    LogUtils.d(TAG, "status = %s", status);
                    String localUriStr = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(localUriStr)) {
                        localUri = Uri.parse(localUriStr);
                    }
                    mimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
                    break;
                }
            }
            cursor.close();
        }
        queryed = true;
    }



    public boolean isDownloaded() {
        if (status != DownloadManager.STATUS_SUCCESSFUL && status != Downloads.STATUS_SUCCESS) {
            return false;
        }
        try {
            MyApplication.getInstance().getContentResolver().openFileDescriptor(localUri, "r").close();
        } catch (Exception exc) {
            return false;
        }
        return true;
    }

    public boolean isDownloading() {
        return status == DownloadManager.STATUS_RUNNING || status == DownloadManager.STATUS_PENDING;
    }

    public boolean isInstalled() {
        if (TextUtils.isEmpty(DeviceInfo.apps) || TextUtils.isEmpty(packageName))
            return false;
        return DeviceInfo.apps.contains(packageName);
    }



    public void setActionImage(ImageView imageView) {
        setActionImage(imageView, false);
    }


    public void setActionImage(ImageView imageView, boolean forceRefresh) {
        if (!queryed || forceRefresh) {
            query();
        }
        if (isInstalled()) {
            imageView.setImageResource(R.drawable.ic_open);
        } else if (isDownloading()) {
            EventBus.getDefault().post(new DownloadStartEvent(downloadUrl));
        } else if (isDownloaded()) {
            imageView.setImageResource(R.drawable.ic_install);
        } else {
            imageView.setImageResource(R.drawable.ic_download);
        }
    }

    public void setActionText(TextView textView, boolean forceRefresh) {
        if (!queryed || forceRefresh) {
            query();
        }
        if (isInstalled()) {
            textView.setText(R.string.open);
        } else if (isDownloaded()) {
            textView.setText(R.string.install);
        } else {
            textView.setText(R.string.download);
        }
    }

    public void handleAction(DownloadManager downloadManager) {
        handleAction(downloadManager, false);
    }
    public void handleAction(DownloadManager downloadManager, boolean forceRefresh) {
        Context context = MyApplication.getInstance();
        if (!queryed || forceRefresh) {
            query();
        }
        if (isInstalled()) {
            Utils.startAPP(packageName);
        } else if (isDownloaded()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(localUri, mimeType);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(context, R.string.download_no_application_title,
                        Toast.LENGTH_LONG).show();
            }
        }else {
            startDownload(downloadManager);
        }

    }

    private void startDownload(DownloadManager downloadManager) {
        LogUtils.d(TAG, "start downloadurl:\n%s", downloadUrl);
        String url = downloadUrl;
        Uri srcUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(srcUri);
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "/");
        request.setPackageName(packageName);
        request.setDescription(getIconUrl());
        downloadManager.enqueue(request);
        EventBus.getDefault().post(new DownloadStartEvent(url));
    }


    public String getInstallRateStr() {
        return new StringBuilder().append(installedMemberNum).append(" / ").append(groupMemberNum).toString();
    }


    public AppModel cloneAppModel() {
        AppModel appModel = new AppModel();
        appModel.content = content;
        appModel.packageName = packageName;
        appModel.groupMemberNum = groupMemberNum;
        appModel.installedMemberNum = installedMemberNum;
        appModel.title = title;
        return appModel;
    }


    public String getTitle() {
        if (TextUtils.isEmpty(title) || "null".equals(title)) {
            return packageName;
        }
        return title;
    }

    public String getTags() {
        if (TextUtils.isEmpty(tags) || "null".equals(tags)) {
            return "";
        }
        return tags;
    }
}
