package com.example.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joliveira on 5/3/17.
 */

public class TrailerModel implements Parcelable {

    public TrailerModel() {

    }

    public TrailerModel(String id, String key, String name, String site) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
    }

    public TrailerModel(Parcel parcel) {
        this.id = parcel.readString();
        this.key = parcel.readString();
        this.name = parcel.readString();
        this.site = parcel.readString();
    }

    private String id;
    private String key;
    private String name;
    private String site;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
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
