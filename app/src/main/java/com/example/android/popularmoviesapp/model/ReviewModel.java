package com.example.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by joliveira on 5/3/17.
 */

public class ReviewModel implements Parcelable {

    private long _id;
    private String idReview;
    private String author;
    private String content;
    private String url;
    private String idMovie;

    public ReviewModel() {

    }

    public ReviewModel(long _id, String idReview, String author, String content, String url, String idMovie) {
        this._id = _id;
        this.idReview = idReview;
        this.author = author;
        this.content = content;
        this.url = url;
        this.idMovie = idMovie;
    }

    public ReviewModel(Parcel parcel) {
        this._id = parcel.readLong();
        this.idReview = parcel.readString();
        this.author = parcel.readString();
        this.content = parcel.readString();
        this.url = parcel.readString();
        this.idMovie = parcel.readString();
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public String getIdReview() {
        return idReview;
    }

    public void setIdReview(String id) {
        this.idReview = idReview;
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
    public String toString() {
        return "ReviewModel{" +
                "_id=" + _id +
                ", idReview='" + idReview + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", idMovie='" + idMovie + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(_id);
        parcel.writeString(idReview);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
        parcel.writeString(idMovie);
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
