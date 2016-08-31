package com.github.jupittar.vmovier.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.base.LazyFragment;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.commlib.custom.recyclerview.decoration.DividerItemDecoration;
import com.github.jupittar.commlib.rxbus.RxBus;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.activities.SeriesDetailActivity;
import com.github.jupittar.vmovier.adapters.MoviesInSeriesAdapter;
import com.github.jupittar.vmovier.models.SeriesDetail;

import butterknife.BindView;

public class MovieInSeriesFragment extends LazyFragment {

    public static final String POST_LIST = "post_list";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private SeriesDetail.Posts mPosts;
    private MoviesInSeriesAdapter mAdapter;

    public static MovieInSeriesFragment newInstance(SeriesDetail.Posts posts) {
        MovieInSeriesFragment fragment = new MovieInSeriesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(POST_LIST, posts);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosts = getArguments().getParcelable(POST_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_in_series, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MoviesInSeriesAdapter(getActivity(), R.layout.item_movie_in_series);
        mAdapter.setOnItemClickListener(new CommonViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SeriesDetail.Posts.PostList item = mAdapter.getDataItem(position);
                String postId = item.getSeries_postid();
                RxBus.getDefault().post(SeriesDetailActivity.BUS_EVENT_CLICK_VIDEO, postId);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onFirstAppear() {
        if (mPosts != null) {
            mAdapter.addAll(mPosts.getList());
        }
    }
}
