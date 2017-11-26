package com.comikz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaDetail implements Parcelable {

    private String mDescription, mImageUrl;

    private List<MangaChapter> mChapters;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDescription);
        dest.writeString(this.mImageUrl);
        dest.writeTypedList(this.mChapters);
    }

    public MangaDetail() {
    }

    protected MangaDetail(Parcel in) {
        this.mDescription = in.readString();
        this.mImageUrl = in.readString();
        this.mChapters = in.createTypedArrayList(MangaChapter.CREATOR);
    }

    public static final Creator<MangaDetail> CREATOR = new Creator<MangaDetail>() {
        @Override
        public MangaDetail createFromParcel(Parcel source) {
            return new MangaDetail(source);
        }

        @Override
        public MangaDetail[] newArray(int size) {
            return new MangaDetail[size];
        }
    };

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public List<MangaChapter> getChapters() {
        return mChapters;
    }

    public void setChapters(List<MangaChapter> chapters) {
        mChapters = chapters;
    }
}
