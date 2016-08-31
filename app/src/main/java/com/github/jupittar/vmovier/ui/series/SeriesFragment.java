package com.github.jupittar.vmovier.ui.series;

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
import com.github.jupittar.vmovier.data.source.DataManager;
import com.github.jupittar.vmovier.data.models.Series;

import java.util.List;

import butterknife.BindView;

/**
 * A fragment representing a list of movie series.
 */
public class SeriesFragment extends LazyFragment implements SeriesMvp.View {

    public static final int MENU_INDEX_SERIES = 2;

    private SeriesPresenter mSeriesPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mSeriesRecyclerView;

    private SeriesAdapter mSeriesAdapter;
    private int mPage = 1;

    public SeriesFragment() {
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
                mSeriesPresenter.fetchSeries(mPage);
            }
        });
        mSeriesPresenter.fetchSeries(mPage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSeriesPresenter = new SeriesPresenter(DataManager.getInstance());
        mSeriesPresenter.attachView(this);
        return inflater.inflate(R.layout.fragment_movie_series, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSeriesPresenter.detachView();
    }

    @Override
    public void showSeries(List<Series> series) {
        mSeriesAdapter.addAll(series);
    }
}
