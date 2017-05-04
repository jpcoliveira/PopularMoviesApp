package com.example.android.popularmoviesapp.model;

import android.content.ClipData;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by joliveira on 4/28/17.
 */

public class MovieModel implements Parcelable {

    private long id;
    private String title;
    private String thumbnail;
    private String synopsis;
    private String rating;
    private String dateRelease;
    private List<TrailerModel> trailers;
    private List<ReviewModel> reviews;


    public MovieModel() {
    }

    public MovieModel(long id, String title, String thumbnail, String synopsis, String rating, String dateRelease, List<TrailerModel> trailers, List<ReviewModel> reviews) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.synopsis = synopsis;
        this.rating = rating;
        this.dateRelease = dateRelease;
        this.trailers = trailers;
        this.reviews = reviews;
    }

    public MovieModel(Parcel parcel) {
        id = parcel.readLong();
        title = parcel.readString();
        thumbnail = parcel.readString();
        synopsis = parcel.readString();
        rating = parcel.readString();
        dateRelease = parcel.readString();

        if (trailers == null)
            trailers = new ArrayList<TrailerModel>();
        parcel.readTypedList(trailers, TrailerModel.CREATOR);

        if (reviews == null)
            reviews = new ArrayList<ReviewModel>();
        parcel.readTypedList(reviews, ReviewModel.CREATOR);

    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }

    public List<TrailerModel> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailerModel> trailers) {
        this.trailers = trailers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    @Override
    public String toString() {
        return "MovieModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", rating=" + rating +
//                ", date='" + date + '\'' +
//                ", trailers=" + trailers +
//                ", reviews=" + reviews +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(thumbnail);
        parcel.writeString(synopsis);
        parcel.writeString(dateRelease);
        parcel.writeString(rating);
        parcel.writeTypedList(trailers);
        parcel.writeTypedList(reviews);
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
