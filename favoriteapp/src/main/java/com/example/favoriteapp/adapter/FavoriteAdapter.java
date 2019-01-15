package com.example.favoriteapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favoriteapp.activity.DetilFavorite;
import com.example.favoriteapp.R;
import com.squareup.picasso.Picasso;

import static com.example.favoriteapp.db.DatabaseContract.FavoriteColumns.DATE;
import static com.example.favoriteapp.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.example.favoriteapp.db.DatabaseContract.FavoriteColumns.NAME;
import static com.example.favoriteapp.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.favoriteapp.db.DatabaseContract.getColumnString;

public class FavoriteAdapter extends CursorAdapter {

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null){
            TextView textViewTitle, textViewOverview, textViewRelease;
            ImageView imgMovie;

            final String loadPoster = "http://image.tmdb.org/t/p/w185" + getColumnString(cursor, POSTER);

            textViewTitle = view.findViewById(R.id.tv_title);
            textViewOverview = view.findViewById(R.id.tv_desc);
            textViewRelease = view.findViewById(R.id.tv_tanggal);
            imgMovie = view.findViewById(R.id.image_view);
            CardView cv_listMovie = view.findViewById(R.id.card_view);
            cv_listMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetilFavorite.class);
                    intent.putExtra("judulMovie", getColumnString(cursor,NAME));
                    intent.putExtra("img", loadPoster);
                    intent.putExtra("desc", getColumnString(cursor,DESCRIPTION));
                    intent.putExtra("tanggal", getColumnString(cursor,DATE));
                    context.startActivity(intent);
                }
            });
            String titik = context.getResources().getString(R.string.titik);

            textViewTitle.setText(getColumnString(cursor,NAME));
            textViewOverview.setText(getColumnString(cursor,DESCRIPTION).substring(0,15)+titik);
            textViewRelease.setText(getColumnString(cursor,DATE));
            Picasso.with(context).load(loadPoster)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imgMovie);
        }
    }
}
