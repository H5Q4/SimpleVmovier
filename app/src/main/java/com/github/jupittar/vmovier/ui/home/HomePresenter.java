package com.github.jupittar.vmovier.ui.home;


import com.github.jupittar.commlib.custom.recyclerview.entity.SectionedItem;
import com.github.jupittar.vmovier.data.models.Banner;
import com.github.jupittar.vmovier.data.models.Movie;
import com.github.jupittar.vmovier.data.source.DataManager;
import com.github.jupittar.vmovier.ui.base.BasePresenter;
import com.orhanobut.logger.Logger;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomePresenter extends BasePresenter<HomeMvp.View> implements HomeMvp.Presenter {

    private DataManager mDataManager;

    public HomePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    void fetchBanner() {
        Subscription subscription = mDataManager.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Banner>>() {
                @Override
                public void call(List<Banner> banners) {
                    List<String> imageUrls = new ArrayList<>();
                    for (Banner banner :
                        banners) {
                        imageUrls.add(banner.getImage());
                    }
                    getMvpView().handleBanner(imageUrls);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Logger.e(throwable, "get banner");
                }
            });
        addSubscription(subscription);
    }

    void fetchMovies(final int pageIndex) {
        Subscription subscription = mDataManager
            .fetLatestMovies(pageIndex, 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Movie>>() {
                @Override
                public void call(List<Movie> movies) {
                    if (movies != null) {
                        List<SectionedItem<Movie>> sectionedItems = new ArrayList<>();
                        String lastTitle = "";
                        if (getMvpView().getMovieCount() > 0) {
                            Movie lastMovie = getMvpView().getLastMovie().getItem();
                            lastTitle = formatTitle(lastMovie);
                        }
                        for (int i = 0; i < movies.size(); i++) {
                            String title = formatTitle(movies.get(i));
                            if (i == 0 && getMvpView().getMovieCount() == 0) {
                                lastTitle = title;
                            }
                            if (!title.equals(lastTitle)) {
                                sectionedItems.add(new SectionedItem<Movie>(true, title));
                                lastTitle = title;
                            }
                            sectionedItems.add(new SectionedItem<>(movies.get(i)));

                        }
                        getMvpView().showMovies(sectionedItems);
                        getMvpView().hideLoading();
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    if (throwable instanceof SocketTimeoutException
                        || throwable instanceof UnknownHostException
                        || throwable instanceof SocketException) {
                        if (pageIndex == 1) {
                            getMvpView().showErrorLayout();
                        } else {
                            getMvpView().showErrorSnackbar("网络错误，加载失败");
                        }
                    }
                    getMvpView().hideLoading();
                    Logger.e(throwable, "getLatestMovies");
                }
            });
        addSubscription(subscription);
    }

    String formatTitle(Movie movie) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH);
        return dateFormat
            .format(new Date(Long.parseLong(movie.getPublish_time()) * 1000))
            .split(",")[0];
    }
}