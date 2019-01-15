package com.example.helsanf.catalogemovie.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.db.DatabaseContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.example.helsanf.catalogemovie.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class DetailFavorite extends AppCompatActivity {
    String img, judulMovie, desc, tanggal;

    @BindView(R.id.tv_detail_judul)
    TextView tvJudul;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_desc_detail)
    TextView tvDesc;
    @BindView(R.id.img_detail)
    ImageView imgView;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite);
        ButterKnife.bind(this);
        img = getIntent().getStringExtra("img");
        judulMovie = getIntent().getStringExtra("judulMovie");
        desc = getIntent().getStringExtra("desc");
        tanggal = getIntent().getStringExtra("tanggal");

        tvJudul.setText(judulMovie);
        tvRating.setText(tanggal);
        tvDesc.setText(desc);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.icon_launcher);
        requestOptions.error(R.drawable.icon_launcher);
        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load("http://image.tmdb.org/t/p/w500"+img)
                .into(imgView);

    }

}
