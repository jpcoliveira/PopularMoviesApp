package com.example.android.popularmoviesapp.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joliveira on 4/28/17.
 */

public class MovieModel extends SugarRecord<MovieModel> implements Parcelable {

    public String idMovie;
    public String title;
    public String thumbnail;
    public String synopsis;
    public String rating;
    public String dateRelease;
    public List<TrailerModel> trailers;
    public List<ReviewModel> reviews;
    public boolean favorite;


    public MovieModel() {

    }

    public MovieModel(String id, String title, String thumbnail, String synopsis, String rating, String dateRelease, List<TrailerModel> trailers, List<ReviewModel> reviews, boolean favorite) {
        this.idMovie = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.synopsis = synopsis;
        this.rating = rating;
        this.dateRelease = dateRelease;
        this.trailers = trailers;
        this.reviews = reviews;
        this.favorite = favorite;
    }

    public MovieModel(Parcel parcel) {
        this.idMovie = parcel.readString();
        this.title = parcel.readString();
        this.thumbnail = parcel.readString();
        this.synopsis = parcel.readString();
        this.dateRelease = parcel.readString();
        this.rating = parcel.readString();
        if (this.trailers == null)
            this.trailers = new ArrayList<TrailerModel>();
        parcel.readTypedList(this.trailers, TrailerModel.CREATOR);

        if (this.reviews == null)
            this.reviews = new ArrayList<ReviewModel>();
        parcel.readTypedList(this.reviews, ReviewModel.CREATOR);
        this.favorite = parcel.readByte() != 0;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
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

    public List<TrailerModel> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailerModel> trailers) {
        this.trailers = trailers;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "idMovie=" + idMovie +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", rating='" + rating + '\'' +
                ", dateRelease='" + dateRelease + '\'' +
                ", trailers=" + trailers +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idMovie);
        parcel.writeString(title);
        parcel.writeString(thumbnail);
        parcel.writeString(synopsis);
        parcel.writeString(dateRelease);
        parcel.writeString(rating);
        parcel.writeTypedList(trailers);
        parcel.writeTypedList(reviews);
        parcel.writeByte((byte) (favorite ? 1 : 0));
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
