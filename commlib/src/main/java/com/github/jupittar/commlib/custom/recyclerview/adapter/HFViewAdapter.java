package com.github.jupittar.commlib.custom.recyclerview.adapter;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.custom.recyclerview.CommonViewHolder;

import java.util.List;

public abstract class HFViewAdapter<T> extends CommonViewAdapter<T> {

    private static final int ITEM_TYPE_HEADER = 0x111;
    private static final int ITEM_TYPE_FOOTER = 0x222;
    private View mHeaderView;
    private View mFooterView;

    public HFViewAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    public void addHeaderView(View view) {
        this.mHeaderView = view;
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        this.mFooterView = view;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        if (holder.getItemViewType() != ITEM_TYPE_HEADER
            && holder.getItemViewType() != ITEM_TYPE_FOOTER) {
            if (mHeaderView != null) {
                position--;
            }
            T item = getDataItem(position);
            convertView(holder, item);
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder holder = null;
        if (mHeaderView != null && viewType == ITEM_TYPE_HEADER) {
            holder = new CommonViewHolder(mHeaderView);
        } else if (mFooterView != null && viewType == ITEM_TYPE_FOOTER) {
            holder = new CommonViewHolder(mFooterView);
        } else {
            holder = super.onCreateViewHolder(parent, viewType);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return ITEM_TYPE_HEADER;
        }

        if (mFooterView != null && position == getItemCountWithHF() - 1) {
            return ITEM_TYPE_FOOTER;
        }

        return appendViewType(mHeaderView == null ? position : --position);
    }

    public int getItemCountIgnoreHF(){
        return super.getItemCount();
    }

    public int getItemCountWithHF() {
        int count = super.getItemCount();
        if (mHeaderView != null) {
            count++;
        }
        if (mFooterView != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemCount() {
        return getItemCountWithHF();
    }

    public abstract int appendViewType(int position);

    private void add(T item) {
        mItemList.add(item);
        notifyItemInserted(mHeaderView == null ? mItemList.size() - 1 : mItemList.size());
    }

    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    public void remove(T item) {
        int position = mItemList.indexOf(item);
        if (position > -1) {
            mItemList.remove(position);
            notifyItemRemoved(mHeaderView == null ? position : position + 1);
        }
    }

    public void clear() {
        while (mItemList.size() > 0) {
            remove(mItemList.get(mHeaderView == null ? 0 : 1));
        }
    }

    public boolean isEmpty() {
        return getItemCountWithHF() == 0;
    }

    public boolean hasHeader() {
        return mHeaderView != null;
    }
}
