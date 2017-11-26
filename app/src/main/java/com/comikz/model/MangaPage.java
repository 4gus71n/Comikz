package com.comikz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Agustin Tomas Larghi on 25/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaPage implements Parcelable {
    private String mImageUrl;
    private int mCurrentPage, mTotalPages;

    public MangaPage(String mImageUrl, int mCurrentPage, int mTotalPages) {
        this.mImageUrl = mImageUrl;
        this.mCurrentPage = mCurrentPage;
        this.mTotalPages = mTotalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mImageUrl);
        dest.writeInt(this.mCurrentPage);
        dest.writeInt(this.mTotalPages);
    }

    public MangaPage() {
    }

    protected MangaPage(Parcel in) {
        this.mImageUrl = in.readString();
        this.mCurrentPage = in.readInt();
        this.mTotalPages = in.readInt();
    }

    public static final Parcelable.Creator<MangaPage> CREATOR = new Parcelable.Creator<MangaPage>() {
        @Override
        public MangaPage createFromParcel(Parcel source) {
            return new MangaPage(source);
        }

        @Override
        public MangaPage[] newArray(int size) {
            return new MangaPage[size];
        }
    };

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }
}
