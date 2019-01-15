package com.example.helsanf.catalogemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.activity.DetailFavorite;
import com.example.helsanf.catalogemovie.activity.DetailMovie;
import com.example.helsanf.catalogemovie.model.Favorite;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.CardViewHolder> {
    private Context context;
    private Cursor cursor;
    private String fix;
    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public FavoriteAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_view, viewGroup, false);
        return new FavoriteAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.CardViewHolder cardViewHolder, int i ) {

        String test = getItem(i).getDescription();

        final Favorite favorite = getItem(i);

        if(TextUtils.isEmpty(test)){
            fix = "Data tidak ada";
        }else{
            fix = test.substring(0,15)+"....";
        }

        cardViewHolder.mTitle.setText(getItem(i).getTitle());
        cardViewHolder.mDesc.setText(fix);
        cardViewHolder.mTanggal.setText(getItem(i).getDate());

        cardViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailActivity = new Intent(context, DetailFavorite.class);
                detailActivity.putExtra("img", favorite.getImage());
                detailActivity.putExtra("judulMovie", favorite.getTitle());
                detailActivity.putExtra("desc", favorite.getDescription());
                detailActivity.putExtra("tanggal", favorite.getDate());
                context.startActivity(detailActivity);
            }
        });
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.icon_launcher);
        requestOptions.error(R.drawable.icon_launcher);
        Glide.with(context.getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load("http://image.tmdb.org/t/p/w500"+getItem(i).getImage())
                .into(cardViewHolder.imgMovie);
    }

    @Override
    public int getItemCount() {
        if(cursor==null)
            return 0;
        return cursor.getCount();
    }

    private Favorite getItem(int position){
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(cursor);
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_desc)
        TextView mDesc;
        @BindView(R.id.tv_tanggal)
        TextView mTanggal;
        @BindView(R.id.image_view)
        ImageView imgMovie;
        @BindView(R.id.card_view)
        CardView cardView;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
