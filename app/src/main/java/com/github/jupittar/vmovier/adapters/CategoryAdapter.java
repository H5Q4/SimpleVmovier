package com.github.jupittar.vmovier.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.custom.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.data.models.Category;

public class CategoryAdapter extends CommonViewAdapter<Category> {

    public CategoryAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convertView(CommonViewHolder holder, Category item) {
        TextView titleTextView = holder.getView(R.id.title_tv);
        titleTextView.setText(String.format("#%s#", item.getCatename()));
        AspectRatioImageView iconImageView = holder.getView(R.id.icon_iv);
        iconImageView.setAspectRatio(AspectRatioImageView.SQUARE);
        Glide.with(mContext)
            .load(item.getIcon())
            .centerCrop()
            .into(iconImageView);
    }
}
