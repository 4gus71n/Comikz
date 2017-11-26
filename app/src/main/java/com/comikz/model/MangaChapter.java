package com.comikz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Agustin Tomas Larghi on 4/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaChapter implements Parcelable {

    private String mTitle, mMangaPermalink, mVolume, mChapter;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mMangaPermalink);
        dest.writeString(this.mVolume);
        dest.writeString(this.mChapter);
    }

    public MangaChapter() {
    }

    protected MangaChapter(Parcel in) {
        this.mTitle = in.readString();
        this.mMangaPermalink = in.readString();
        this.mVolume = in.readString();
        this.mChapter = in.readString();
    }

    public static final Creator<MangaChapter> CREATOR = new Creator<MangaChapter>() {
        @Override
        public MangaChapter createFromParcel(Parcel source) {
            return new MangaChapter(source);
        }

        @Override
        public MangaChapter[] newArray(int size) {
            return new MangaChapter[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMangaPermalink() {
        return mMangaPermalink;
    }

    public void setMangaPermalink(String mangaPermalink) {
        mMangaPermalink = mangaPermalink;
    }

    public String getVolume() {
        return mVolume;
    }

    public void setVolume(String volume) {
        mVolume = volume;
    }

    public String getChapter() {
        return mChapter;
    }

    public void setChapter(String chapter) {
        mChapter = chapter;
    }
}
