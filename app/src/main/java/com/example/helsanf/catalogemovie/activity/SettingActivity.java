package com.example.helsanf.catalogemovie.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.service.ReminderPreference;
import com.example.helsanf.catalogemovie.service.ReminderReceiver;
import com.example.helsanf.catalogemovie.service.ReminderRelease;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.HEADER_DAILY;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.HEADER_UPCOMING;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.KEY_DAILY_REMINDER;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.KEY_UPCOMING;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.REMINDER_RECIEVE;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.TYPE_PREFERENCES;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.reminderHarian)
    Switch pengingatHarian;
    @BindView(R.id.reminderRelease)
    Switch pengingatRelease;

    public ReminderReceiver receiverDaily;
    public ReminderRelease receiverRelease;
    public ReminderPreference reminderPreference;
    public SharedPreferences sReleaseReminder, sDailyReminder;
    public SharedPreferences.Editor editRelease, editDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        receiverDaily = new ReminderReceiver();
        receiverRelease = new ReminderRelease();
        reminderPreference = new ReminderPreference(this);
        setPreference();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @OnCheckedChanged(R.id.reminderHarian)
    public  void  setDailyRemind(boolean isChecked){
        editDaily = sDailyReminder.edit();
        if (isChecked) {
            editDaily.putBoolean(KEY_DAILY_REMINDER, true);
            editDaily.commit();
            remainderDailyOn();
        } else {
            editDaily.putBoolean(KEY_DAILY_REMINDER, false);
            editDaily.commit();
            remainderDailyOf();
        }
    }
    @OnCheckedChanged(R.id.reminderRelease)
    public  void setReleaseRemind(boolean checked){
        editRelease = sReleaseReminder.edit();
        if (checked) {
            editRelease.putBoolean(KEY_UPCOMING, true);
            editRelease.commit();
            remainderReleaseOn();
        } else {
            editRelease.putBoolean(KEY_UPCOMING, false);
            editRelease.commit();
            remainderReleaseOff();
        }
    }


    private void  setPreference(){
        sReleaseReminder = getSharedPreferences(HEADER_UPCOMING, MODE_PRIVATE);
        sDailyReminder = getSharedPreferences(HEADER_DAILY, MODE_PRIVATE);
        boolean cUpcomingReminder = sReleaseReminder.getBoolean(KEY_UPCOMING, false);
        pengingatRelease.setChecked(cUpcomingReminder);
        boolean cDailyReminder = sDailyReminder.getBoolean(KEY_DAILY_REMINDER, false);
        pengingatHarian.setChecked(cDailyReminder);
    }

    private void remainderReleaseOn() {
        String time = "08:00";
        String message = "Release Movie, Mohon tunggu sebentar";
        reminderPreference.setReleaseTime(time);
        reminderPreference.setReleaseMessage(message);
        receiverRelease.setReminder(SettingActivity.this, TYPE_PREFERENCES, time, message);
    }

    private void remainderReleaseOff() {
        receiverRelease.cancelReminder(SettingActivity.this);
    }
    private void remainderDailyOn() {
        String time = "07:00";
        String message = "Film harian, Mohon tunggu sebentar";
        reminderPreference.setDailyTime(time);
        reminderPreference.setDailyMessage(message);
        receiverDaily.setReminder(SettingActivity.this, REMINDER_RECIEVE, time, message);
    }

    private void remainderDailyOf() {
        receiverDaily.cancelReminder(SettingActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
