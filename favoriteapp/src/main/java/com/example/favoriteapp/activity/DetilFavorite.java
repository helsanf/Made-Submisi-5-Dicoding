package com.example.favoriteapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favoriteapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetilFavorite extends AppCompatActivity {
    String img, judulMovie, desc, tanggal;

    @BindView(R.id.tv_detail_judul)
    TextView tvJudul;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_desc_detail)
    TextView tvDesc;
    @BindView(R.id.img_detail)
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_favorite);
        ButterKnife.bind(this);
        img = getIntent().getStringExtra("img");
        judulMovie = getIntent().getStringExtra("judulMovie");
        desc = getIntent().getStringExtra("desc");
        tanggal = getIntent().getStringExtra("tanggal");

        tvJudul.setText(judulMovie);
        tvRating.setText(tanggal);
        tvDesc.setText(desc);
        Picasso.with(getApplicationContext())
                .load(img)
                .into(imgView);
    }
}
