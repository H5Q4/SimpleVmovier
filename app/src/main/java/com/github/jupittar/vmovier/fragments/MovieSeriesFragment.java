package com.github.jupittar.vmovier.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.base.LazyFragment;
import com.github.jupittar.commlib.custom.recyclerview.EndlessScrollListener;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.activities.SeriesDetailActivity;
import com.github.jupittar.vmovier.adapters.SeriesAdapter;
import com.github.jupittar.vmovier.models.Series;
import com.github.jupittar.vmovier.network.ExtractDataFunc;
import com.github.jupittar.vmovier.network.ServiceGenerator;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A fragment representing a list of movie series.
 */
public class MovieSeriesFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mSeriesRecyclerView;

    private SeriesAdapter mSeriesAdapter;
    private int mPage = 1;

    public MovieSeriesFragment() {
    }

    @Override
    public void onFirstAppear() {
        setUpSeriesRecyclerView();
    }

    private void setUpSeriesRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mSeriesRecyclerView.setLayoutManager(layoutManager);
        mSeriesRecyclerView.setHasFixedSize(true);
        mSeriesAdapter = new SeriesAdapter(getActivity(), R.layout.item_series);
        mSeriesRecyclerView.setAdapter(mSeriesAdapter);
        mSeriesAdapter.setOnItemClickListener(new CommonViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Series series = mSeriesAdapter.getDataItem(position);
                Intent intent = new Intent(getActivity(), SeriesDetailActivity.class);
                intent.putExtra(SeriesDetailActivity.SERIES_ID, series.getSeriesid());
                startActivity(intent);
            }
        });
        mSeriesRecyclerView.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                mPage++;
                fetchSeries();
            }
        });
        fetchSeries();
    }

    private void fetchSeries() {
        Subscription subscription = ServiceGenerator
            .getVMovieService()
            .getSeriesList(mPage, 10)
            .map(new ExtractDataFunc<List<Series>>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Series>>() {
                @Override
                public void call(List<Series> series) {
                    if (series != null) {
                        mSeriesAdapter.addAll(series);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_series, container, false);
    }

}
