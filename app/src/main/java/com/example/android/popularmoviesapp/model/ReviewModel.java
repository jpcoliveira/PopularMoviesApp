package com.example.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by joliveira on 5/3/17.
 */

public class ReviewModel extends SugarRecord<ReviewModel> implements Parcelable {

    private String idReview;
    private String author;
    private String content;
    private String url;

    public ReviewModel() {

    }

    public ReviewModel(String idReview, String author, String content, String url) {
        this.idReview = idReview;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public ReviewModel(Parcel parcel) {
        this.idReview = parcel.readString();
        this.author = parcel.readString();
        this.content = parcel.readString();
        this.url = parcel.readString();
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
                "idReview=" + idReview +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idReview);
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
