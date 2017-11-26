package com.comikz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class Manga implements Parcelable {

    private String mTitle, mImage, mId, mRaking;
    private List<String> mCategories;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Manga) {
            Manga anotherManga = (Manga) obj;
            return mId.equals(anotherManga.getId());
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mImage);
        dest.writeString(this.mId);
        dest.writeString(this.mRaking);
        dest.writeStringList(this.mCategories);
    }

    public Manga() {
    }

    protected Manga(Parcel in) {
        this.mTitle = in.readString();
        this.mImage = in.readString();
        this.mId = in.readString();
        this.mRaking = in.readString();
        this.mCategories = in.createStringArrayList();
    }

    public static final Creator<Manga> CREATOR = new Creator<Manga>() {
        @Override
        public Manga createFromParcel(Parcel source) {
            return new Manga(source);
        }

        @Override
        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getRanking() {
        return mRaking;
    }

    public void setRanking(String votes) {
        mRaking = votes;
    }

    public List<String> getCategories() {
        return mCategories;
    }

    public void setCategories(List<String> categories) {
        mCategories = categories;
    }
}
