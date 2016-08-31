package com.github.jupittar.commlib.rxbus;

import rx.Subscriber;

/**
 * A helper abstract class used to subscribe the observable returned by RxBus
 *
 * Created by Jupittar on 2016/4/29.
 */
public abstract class BusSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        received(t);
    }

    protected abstract void received(T t);
}
