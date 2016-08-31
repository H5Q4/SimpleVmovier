package com.github.jupittar.vmovier.ui.base;


import rx.Subscription;

public interface Mvp {

    interface View {

    }

    interface Presenter<V extends View> {

        void attachView(V v);

        void detachView();

        void addSubscription(Subscription s);

        void unsubscribeAll();

    }
}
