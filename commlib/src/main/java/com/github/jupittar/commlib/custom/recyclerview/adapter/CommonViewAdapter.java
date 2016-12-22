package com.github.jupittar.commlib.custom.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.custom.recyclerview.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonViewAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

  protected Context mContext;
  @LayoutRes
  protected int mLayoutId;
  protected List<T> mItemList;

  protected OnItemClickListener mOnItemClickListener;

  public CommonViewAdapter(Context context, @LayoutRes int layoutId) {
    this.mContext = context;
    this.mLayoutId = layoutId;
    mItemList = new ArrayList<>();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  @Override
  public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
    final CommonViewHolder holder = new CommonViewHolder(view);
    if (mOnItemClickListener != null) {
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
        }
      });
    }
    return holder;
  }

  @Override
  public void onBindViewHolder(CommonViewHolder holder, int position) {
    T item = getDataItem(position);
    convertView(holder, item);
  }

  private void add(T item) {
    mItemList.add(item);
    notifyItemInserted(mItemList.size() - 1);
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
      notifyItemRemoved(position);
    }
  }

  public void clear() {
    while (getItemCount() > 0) {
      remove(getDataItem(0));
    }
  }

  public boolean isEmpty() {
    return getItemCount() == 0;
  }

  public T getDataItem(int position) {
    return mItemList.get(position);
  }

  @Override
  public int getItemCount() {
    return mItemList.size();
  }

  public List<T> getItemList() {
    return this.mItemList;
  }

  public abstract void convertView(CommonViewHolder holder, T item);

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
  }
}
