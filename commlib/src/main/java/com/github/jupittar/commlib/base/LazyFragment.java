package com.github.jupittar.commlib.base;

import android.os.Bundle;

public abstract class LazyFragment extends SubscriptionFragment {

    private boolean mIsPrepared;
    private boolean mIsFirstResume = true;
    private boolean mIsFirstPause = true;
    private boolean mIsFirstVisibleToUser = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doWithPreparing();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsFirstResume) {
            mIsFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onAppear();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onDisappear();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mIsFirstVisibleToUser) {
                mIsFirstVisibleToUser = false;
                doWithPreparing();
            } else {
                onAppear();
            }
        } else {
            if (mIsFirstPause) {
                mIsFirstPause = false;
                return;
            }
            onDisappear();
        }
    }

    private synchronized void doWithPreparing() {
        if (mIsPrepared) {
            onFirstAppear();
        } else {
            mIsPrepared = true;
        }
    }

    /**
     * Do some initializations when first appearing
     */
    public abstract void onFirstAppear();

    public void onAppear(){};

    public void onDisappear(){};
}
