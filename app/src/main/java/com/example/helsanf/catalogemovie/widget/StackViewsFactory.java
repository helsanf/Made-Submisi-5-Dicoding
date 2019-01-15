package com.example.helsanf.catalogemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.adapter.AdapterCardMovie;
import com.example.helsanf.catalogemovie.model.Favorite;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class StackViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Favorite> mWidgetItems = new ArrayList<>();
    private Context mContext;

    StackViewsFactory(Context context, Intent intent) {
        mContext = context;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mWidgetItems.clear();
        final long identityToken = Binder.clearCallingIdentity();
        Cursor cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite(cursor);
                mWidgetItems.add(favorite);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        if (cursor != null) {
            cursor.close();
        }
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Favorite currentMovieFavorite ;
        Bundle extra = new Bundle();
        Bitmap bitmap = null;
        String releaseDate = null;
        RequestOptions requestOptions = new RequestOptions();
        try {
            currentMovieFavorite = mWidgetItems.get(i);
            requestOptions.error(new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimary)));
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w185/" + currentMovieFavorite.getImage())
//                    .error(new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimary)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

            releaseDate = currentMovieFavorite.getDate();
            extra.putString(AdapterCardMovie.EXTRA_MOVIE,currentMovieFavorite.getTitle());

        } catch (InterruptedException | ExecutionException | IndexOutOfBoundsException e) {
            Log.d("Widget Load Error", "error");
        }

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        remoteViews.setImageViewBitmap(R.id.posterWidget, bitmap);
        remoteViews.setTextViewText(R.id.realased_date, releaseDate);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extra);

        remoteViews.setOnClickFillInIntent(R.id.posterWidget, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
