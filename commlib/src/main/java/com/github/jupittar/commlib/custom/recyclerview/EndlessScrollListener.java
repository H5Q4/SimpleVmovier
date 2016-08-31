package com.github.jupittar.commlib.custom.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int mPreviousTotal = 0; // the total number of items in the dataset after the last loading.
    private int mVisibleThreshold = 3; // the min amount of items to have below current scroll position before loading more.
    private boolean mLoading = true; // true if still waiting for the last set of data to load

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstVisibleItem = 0;
        int visibleItemCount;
        int totalItemCount = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        visibleItemCount = layoutManager.getChildCount();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
            totalItemCount = gridLayoutManager.getItemCount();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            totalItemCount = linearLayoutManager.getItemCount();
        }

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }

        if (!mLoading && totalItemCount - firstVisibleItem - visibleItemCount <= mVisibleThreshold) {
            onLoadMore();
            mLoading = true;
        }

    }

    /**
     * When the data is refreshed, invoke this to change mPreviousTotal to 0.
     */
    public void onDataClear() {
        mPreviousTotal = 0;
    }

    public void setVisibleThreshold(int visibleThreshold) {
        mVisibleThreshold = visibleThreshold;
    }

    public abstract void onLoadMore();
}
