package com.example.favoriteapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.favoriteapp.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.example.favoriteapp.db.DatabaseContract.getColumnInt;
import static com.example.favoriteapp.db.DatabaseContract.getColumnString;

public class Favorite implements Parcelable {
    private int id;
    private String title;
    private String image;
    private String date;
    private String description;

    public Favorite() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Favorite(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.FavoriteColumns.NAME);
        this.image = getColumnString(cursor, DatabaseContract.FavoriteColumns.POSTER);
        this.date = getColumnString(cursor, DatabaseContract.FavoriteColumns.DATE);
        this.description = getColumnString(cursor, DatabaseContract.FavoriteColumns.DESCRIPTION);
    }
    public Favorite(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        date = in.readString();
        description = in.readString();
    }

    public static final Parcelable.Creator<Favorite> CREATOR = new Parcelable.Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(date);
        parcel.writeString(description);
    }
}
