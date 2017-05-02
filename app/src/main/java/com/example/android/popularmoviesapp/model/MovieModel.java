package com.example.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by joliveira on 4/28/17.
 */

public class MovieModel implements Parcelable {

    private String title;
    private String thumbnail;
    private String synopsis;
    private double rating;
    private String date;

    public MovieModel() {

    }

    public MovieModel(String title, String thumbnail, String synopsis, double rating, String date) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.synopsis = synopsis;
        this.rating = rating;
        this.date = date;
    }

    public MovieModel(Parcel parcel) {
        this.title = parcel.readString();
        this.thumbnail = parcel.readString();
        this.synopsis = parcel.readString();
        this.rating = parcel.readDouble();
        this.date = parcel.readString();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", rating=" + rating +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(thumbnail);
        parcel.writeString(synopsis);
        parcel.writeString(date);
        parcel.writeDouble(rating);
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel parcel) {
            return new MovieModel(parcel);
        }

        @Override
        public MovieModel[] newArray(int i) {
            return new MovieModel[i];
        }
    };
}
