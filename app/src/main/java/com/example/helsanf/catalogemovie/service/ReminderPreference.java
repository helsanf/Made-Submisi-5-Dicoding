package com.example.helsanf.catalogemovie.service;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.MESSAGE_DAILY;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.MESSAGE_RELEASE;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.PREFERENCES_NAME;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.REMINDER_DAILY;

public class ReminderPreference {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor edit;

    public ReminderPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
    }
    public void setReleaseTime(String waktu){
        edit.putString(REMINDER_DAILY,waktu);
        edit.commit();
    }
    public void setReleaseMessage(String pesan){
        edit.putString(MESSAGE_RELEASE,pesan);
        edit.commit();
    }
    public void setDailyTime(String waktu){
        edit.putString(REMINDER_DAILY,waktu);
        edit.commit();
    }
    public void setDailyMessage(String pesan){
        edit.putString(MESSAGE_DAILY,pesan);
    }

}
