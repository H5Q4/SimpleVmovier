package com.github.jupittar.commlib.base;

import android.os.Bundle;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class SubscriptionActivity extends ButterKnifeActivity {

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    /**
     * Remembers to invoke this method to add the subscription to mCompositeSubscription after using it.
     */
    public void addSubscription(Subscription subscription) {
        getCompositeSubscription().add(subscription);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}
