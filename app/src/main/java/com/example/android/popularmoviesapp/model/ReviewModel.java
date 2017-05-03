package com.example.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joliveira on 5/3/17.
 */

public class ReviewModel implements Parcelable {

    private long id;
    private String author;
    private String content;
    private String url;

    public ReviewModel() {

    }

    public ReviewModel(long id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public ReviewModel(Parcel parcel) {
        this.id = parcel.readLong();
        this.author = parcel.readString();
        this.content = parcel.readString();
        this.url = parcel.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }

    public static final Parcelable.Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
        @Override
        public ReviewModel createFromParcel(Parcel parcel) {
            return new ReviewModel(parcel);
        }

        @Override
        public ReviewModel[] newArray(int i) {
            return new ReviewModel[i];
        }
    };
}
