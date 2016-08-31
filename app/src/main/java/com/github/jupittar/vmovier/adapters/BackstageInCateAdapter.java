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

public class BackstageInCateAdapter extends CommonViewAdapter<Movie> {

    public BackstageInCateAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convertView(CommonViewHolder holder, Movie item) {
        AspectRatioImageView thumbnailImageView = holder.getView(R.id.video_thumbnail_iv);
        thumbnailImageView.setAspectRatio(AspectRatioImageView.PHI);
        Glide.with(mContext)
                .load(item.getImage())
                .centerCrop()
                .into(thumbnailImageView);
        TextView titleTextView = holder.getView(R.id.title_tv);
        titleTextView.setText(item.getTitle());
        TextView shareCountTextView = holder.getView(R.id.share_count_tv);
        shareCountTextView.setText(item.getShare_num());
        TextView likeCountTextView = holder.getView(R.id.like_count_tv);
        likeCountTextView.setText(item.getLike_num());
    }
}
