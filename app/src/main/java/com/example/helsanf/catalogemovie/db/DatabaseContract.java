package com.example.helsanf.catalogemovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.helsanf.catalogemovie";
    public static final String SCHEME = "content";

    public static final class FavoriteColumns implements BaseColumns {

        public static String TABLE_FAVORITE = "favorite";

        public static String NAME = "name";
        public static String POSTER = "poster";
        public static String DATE = "date";
        public static String DESCRIPTION = "description";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();

        public static final String
                HEADER_UPCOMING             = "pengingatUpcomming",
                HEADER_DAILY                = "pengingatHarian",
                KEY_UPCOMING                = "checkUpcoming",
                KEY_DAILY_REMINDER          = "chekHarian",
                PREFERENCES_NAME            = "reminderPreferences",
                REMINDER_DAILY              = "waktuHarian",
                MESSAGE_RELEASE             = "pengingatPesanRelease",
                MESSAGE_DAILY               = "pesanDaily",
                EXTRA_MESSAGE_PREFERENCES   = "pesan",
                EXTRA_TYPE_PREFERENCES      = "tipe",
                TYPE_PREFERENCES            = "alarmPengingat",
                MESSAGE_RECIEVE             = "pesanRelease",
                TYPE_RECIEVE                = "tipeRelease",
                REMINDER_RECIEVE            = "alarmPengingatRelease",
                LANGUANGE                   = "en-US";


    }
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

}
