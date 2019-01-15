package com.example.helsanf.catalogemovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.DATE;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.NAME;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "db_favorite";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," + " %s TEXT NOT NULL," + " %s TEXT NOT NULL," + " %s TEXT NOT NULL)",
            TABLE_FAVORITE, _ID, NAME, POSTER, DATE, DESCRIPTION
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_FAVORITE);
        onCreate(sqLiteDatabase);
    }
}
