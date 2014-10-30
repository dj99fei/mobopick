package com.cyou.mobopick.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengfei on 14-10-24.
 */
public class ImageText implements Parcelable {

    public List<String> texts;
    public String imageUrl;

    public ImageText(List<String> texts, String imageUrl) {
        this.texts = texts;
        this.imageUrl = imageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(texts);
        dest.writeString(this.imageUrl);
    }

    private ImageText(Parcel in) {
        this.texts = new ArrayList<String>();
        in.readStringList(this.texts);
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
