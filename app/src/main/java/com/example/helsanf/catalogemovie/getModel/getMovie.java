package com.example.helsanf.catalogemovie.getModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.helsanf.catalogemovie.model.MovieItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getMovie implements Parcelable {


    @SerializedName("page")
    @Expose
    private Integer page;

    protected getMovie(Parcel in) {
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readInt();
        }
        results = in.createTypedArrayList(MovieItem.CREATOR);
    }

    public static final Creator<getMovie> CREATOR = new Creator<getMovie>() {
        @Override
        public getMovie createFromParcel(Parcel in) {
            return new getMovie(in);
        }

        @Override
        public getMovie[] newArray(int size) {
            return new getMovie[size];
        }
    };

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @SerializedName("results")
    private List<MovieItem> results = null;

    public List<MovieItem> getResults() {
        return results;
    }

    public void setResults(List<MovieItem> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (page == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(page);
        }
        parcel.writeTypedList(results);
    }
}
