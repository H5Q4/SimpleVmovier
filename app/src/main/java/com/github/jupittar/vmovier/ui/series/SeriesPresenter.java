package com.github.jupittar.vmovier.ui.series;

import com.github.jupittar.vmovier.data.models.Series;
import com.github.jupittar.vmovier.data.source.DataManager;
import com.github.jupittar.vmovier.ui.base.BasePresenter;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SeriesPresenter extends BasePresenter<SeriesMvp.View> implements SeriesMvp.Presenter {

    private DataManager mDataManager;

    public SeriesPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    public void fetchSeries(int pageIndex) {
        Subscription subscription = mDataManager
            .fetchMovieSeries(pageIndex, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Series>>() {
                @Override
                public void call(List<Series> series) {
                    if (series != null) {
                        getMvpView().showSeries(series);
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Logger.e(throwable, "get series list");
                }
            });
        addSubscription(subscription);
    }
}