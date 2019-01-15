package com.example.helsanf.catalogemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.helsanf.catalogemovie.model.Favorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.DATE;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.NAME;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.TABLE_FAVORITE;

public class FavoriteHelper  {

    private static String DATABASE = TABLE_FAVORITE;
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }
    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<Favorite> query() {
        ArrayList<Favorite> arrayList = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE
                , null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {

                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                favorite.setImage(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favorite.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                favorite.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE,null
                ,_ID + " = ?", new String[]{id},null,null,null,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE
                ,null,null,null,null,null,_ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE,null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE,values,_ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE,_ID + " = ?", new String[]{id});
    }
}
