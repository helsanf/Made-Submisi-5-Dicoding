package com.example.helsanf.catalogemovie.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.activity.MainActivity;

import java.util.Calendar;

import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.EXTRA_MESSAGE_PREFERENCES;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.EXTRA_TYPE_PREFERENCES;

public class ReminderReceiver extends BroadcastReceiver {
    private final int NOTIFY_REMINDER = 1;
    public ReminderReceiver(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(EXTRA_MESSAGE_PREFERENCES);
        String title = "Reminder";
        int NotifId = NOTIFY_REMINDER;

        remainderNotification(context,title,message,NotifId);
    }

    private void remainderNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIFY_REMINDER,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,message)
                .setSmallIcon(R.drawable.black_18dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        notificationManagerCompat.notify(notifId,builder.build());

    }
    public void setReminder (Context context,String type,String time,String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE_PREFERENCES,message);
        intent.putExtra(EXTRA_TYPE_PREFERENCES,type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        int requestCode = NOTIFY_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context,R.string.saveDaily,Toast.LENGTH_SHORT).show();
    }
    public void cancelReminder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ReminderReceiver.class);
        int requestCode = NOTIFY_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context,R.string.cancelDaily, Toast.LENGTH_SHORT).show();
    }
}
