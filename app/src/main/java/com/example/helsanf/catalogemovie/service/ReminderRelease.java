package com.example.helsanf.catalogemovie.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.activity.DetailMovie;
import com.example.helsanf.catalogemovie.getModel.getMovie;
import com.example.helsanf.catalogemovie.model.MovieItem;
import com.example.helsanf.catalogemovie.res.Api;
import com.example.helsanf.catalogemovie.res.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.MESSAGE_RECIEVE;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.TYPE_RECIEVE;

public class ReminderRelease extends BroadcastReceiver {
    private final int NOTIFY_RELEASE = 2;
    public List<MovieItem> listMovie = new ArrayList<>();
    public ReminderRelease(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getUpComing(context);
    }

    public void setReminder(Context context, String type, String time, String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderRelease.class);
        intent.putExtra(MESSAGE_RECIEVE,message);
        intent.putExtra(TYPE_RECIEVE,type);
        String timeArray[] = time.split(":");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        c.set(Calendar.SECOND,0);
        int requestCode = NOTIFY_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context,R.string.save, Toast.LENGTH_SHORT).show();
    }
    public void cancelReminder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ReminderRelease.class);
        int requestCode = NOTIFY_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context,R.string.cancel, Toast.LENGTH_SHORT).show();
    }


    private void getUpComing(final Context context) {
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<getMovie> call = apiInterface.getUpComing(ApiInterface.API_KEY, ApiInterface.BAHASA, 1);
        call.enqueue(new Callback<getMovie>() {
            @Override
            public void onResponse(Call<getMovie> call, Response<getMovie> response) {
                List<MovieItem> list = response.body().getResults();
                listMovie = response.body().getResults();

                int index = new Random().nextInt(list.size());
                MovieItem item = list.get(index);

                int notifId = 300;
                String title = list.get(index).getTitle();
                String message = list.get(index).getDeskripsi();
                showNotification(context, title, message, notifId, item);
            }

            @Override
            public void onFailure(Call<getMovie> call, Throwable t) {

            }
        });
    }
    private void showNotification(Context context, String title, String message, int notifId, MovieItem item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailMovie.class);
        intent.putExtra("movieId",item.getId());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, message)
                .setSmallIcon(R.drawable.black_18dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        notificationManagerCompat.notify(notifId, builder.build());
    }

}
