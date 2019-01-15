package com.example.helsanf.catalogemovie.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.adapter.AdapterCardMovie;

import java.util.Objects;

/**
 * Implementation of App Widget functionality.
 */
public class BannerWidget extends AppWidgetProvider {
    int appWidgetId, viewIndex;
    public static final String TOAST_ACTION = "com.example.helsanf.catalogemovie.TOAST_ACTION";
    public static final String ITEM_EXTRA = "com.example.helsanf.catalogemovie.ITEM_EXTRA";
    public static final String FAVORITE_ACTION = "com.example.helsanf.catalogemovie.FAVORITE_ACTION";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews v = new RemoteViews(context.getPackageName(), R.layout.banner_widget);
        v.setRemoteAdapter(R.id.stack, intent);
        v.setEmptyView(R.id.stack, R.id.no_view);

        Intent toastIntent = new Intent(context, BannerWidget.class);
        toastIntent.setAction(BannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        v.setPendingIntentTemplate(R.id.stack, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, v);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        switch (Objects.requireNonNull(intent.getAction())) {
            case TOAST_ACTION:
                appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                viewIndex = intent.getIntExtra(ITEM_EXTRA, 0);
                String title  = intent.getStringExtra(AdapterCardMovie.EXTRA_MOVIE);

                Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
                break;
            case FAVORITE_ACTION: {
                Log.d("TAG", "onReceive: " + "RECEIVE");
                int widgetIDs[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, BannerWidget.class));
                onUpdate(context, appWidgetManager, widgetIDs);
                appWidgetManager.notifyAppWidgetViewDataChanged(widgetIDs, R.id.stack);
                break;

            }
        }
        super.onReceive(context, intent);
    }
}

