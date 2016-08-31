package com.github.jupittar.vmovier.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.custom.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.models.Movie;

import java.util.Locale;

public class MoviesInCateAdapter extends CommonViewAdapter<Movie> {


    public MoviesInCateAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convertView(CommonViewHolder holder, Movie movie) {
        AspectRatioImageView coverImageView = holder.getView(R.id.item_movie_cover);
        TextView titleTextView = holder.getView(R.id.item_movie_title);
        TextView cateTimeTextView = holder.getView(R.id.cate_time_tv);
        coverImageView.setAspectRatio(AspectRatioImageView.PHI);
        Glide.with(coverImageView.getContext())
            .load(movie.getImage())
            .centerCrop()
            .into(coverImageView);
        titleTextView.setText(movie.getTitle());
        int duration = Integer.parseInt(movie.getDuration());
        int seconds = duration % 60;
        String secondStr = String.valueOf(seconds);
        if (seconds < 10) {
            secondStr = "0" + seconds;
        }
        String time = String.format(Locale.getDefault(), "%d'%s\"", duration / 60, secondStr);
        cateTimeTextView.setText(String.format("%s / %s", movie.getCates().get(0).getCatename(), time));
    }
}
