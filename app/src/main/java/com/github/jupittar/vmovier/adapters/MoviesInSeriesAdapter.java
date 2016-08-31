package com.github.jupittar.vmovier.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.custom.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.models.SeriesDetail;

import java.util.Locale;

public class MoviesInSeriesAdapter extends CommonViewAdapter<SeriesDetail.Posts.PostList> {

    public MoviesInSeriesAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convertView(CommonViewHolder holder, SeriesDetail.Posts.PostList item) {
        AspectRatioImageView thumbnailImageView = holder.getView(R.id.video_thumbnail_iv);
        thumbnailImageView.setAspectRatio(AspectRatioImageView.PHI);
        Glide
            .with(mContext)
            .load(item.getThumbnail())
            .centerCrop()
            .into(thumbnailImageView);
        TextView timeTextView = holder.getView(R.id.time_tv);
        int duration = Integer.parseInt(item.getDuration());
        int seconds = duration % 60;
        String secondStr = String.valueOf(seconds);
        if (seconds < 10) {
            secondStr = "0" + seconds;
        }
        String time = String.format(Locale.getDefault(), "%d:%s", duration / 60, secondStr);
        timeTextView.setText(time);
        TextView titleTextView = holder.getView(R.id.title_tv);
        titleTextView.setText(String.format("第%s集 %s", item.getNumber(), item.getTitle()));
        TextView dateTextView = holder.getView(R.id.date_tv);
        dateTextView.setText(item.getAddtime());
    }
}
