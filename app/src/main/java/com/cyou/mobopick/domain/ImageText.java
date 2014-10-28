package com.cyou.mobopick.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chengfei on 14-10-24.
 */
public class ImageText implements Parcelable {

    public String text;
    public String imageUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeString(this.imageUrl);
    }

    public ImageText() {
    }

    private ImageText(Parcel in) {
        this.text = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<ImageText> CREATOR = new Creator<ImageText>() {
        public ImageText createFromParcel(Parcel source) {
            return new ImageText(source);
        }

        public ImageText[] newArray(int size) {
            return new ImageText[size];
        }
    };
}
