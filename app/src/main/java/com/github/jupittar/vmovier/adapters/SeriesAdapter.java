package com.github.jupittar.vmovier.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.custom.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.models.Series;


public class SeriesAdapter extends CommonViewAdapter<Series> {

    public SeriesAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convertView(CommonViewHolder holder, Series item) {
        AspectRatioImageView coverImageView = holder.getView(R.id.cover_iv);
        TextView titleTextView = holder.getView(R.id.title_tv);
        TextView stateTextView = holder.getView(R.id.state_tv);
        TextView briefTextView = holder.getView(R.id.brief_tv);
        coverImageView.setAspectRatio(5.0D/9.0D);
        Glide.with(mContext)
            .load(item.getImage())
            .centerCrop()
            .into(coverImageView);
        titleTextView.setText(item.getTitle());
        stateTextView.setText(String.format("已更新至%s集  %s人已订阅",
            item.getUpdate_to(), item.getFollower_num()));
        briefTextView.setText(item.getContent());
    }
}
