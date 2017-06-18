package com.example.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by joliveira on 5/3/17.
 */

public class TrailerModel implements Parcelable {

    private long _id;
    private String idTrailer;
    private String key;
    private String name;
    private String site;
    private String idMovie;

    public TrailerModel() {

    }

    public TrailerModel(long _id, String idTrailer, String key, String name, String site, String idMovie) {
        this._id = _id;
        this.idTrailer = idTrailer;
        this.key = key;
        this.name = name;
        this.site = site;
        this.idMovie = idMovie;
    }

    public TrailerModel(Parcel parcel) {
        this._id = parcel.readLong();
        this.idTrailer = parcel.readString();
        this.key = parcel.readString();
        this.name = parcel.readString();
        this.site = parcel.readString();
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

    public String getIdTrailer() {
        return idTrailer;
    }

    public void setIdTrailer(String idTrailer) {
        this.idTrailer = idTrailer;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "TrailerModel{" +
                "_id=" + _id +
                ", idTrailer='" + idTrailer + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
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
        parcel.writeString(idTrailer);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(idMovie);
    }

    public static final Parcelable.Creator<TrailerModel> CREATOR = new Creator<TrailerModel>() {
        @Override
        public TrailerModel createFromParcel(Parcel parcel) {
            return new TrailerModel(parcel);
        }

        @Override
        public TrailerModel[] newArray(int i) {
            return new TrailerModel[i];
        }
    };
}
