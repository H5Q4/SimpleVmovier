package com.github.jupittar.vmovier.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.base.LazyFragment;
import com.github.jupittar.commlib.custom.recyclerview.EndlessScrollListener;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.commlib.custom.recyclerview.decoration.DividerItemDecoration;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.activities.BackstageDetailActivity;
import com.github.jupittar.vmovier.adapters.BackstageInCateAdapter;
import com.github.jupittar.vmovier.models.Movie;
import com.github.jupittar.vmovier.network.ExtractDataFunc;
import com.github.jupittar.vmovier.network.ServiceGenerator;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BackstageByCateFragment extends LazyFragment {

  private static final String ARG_PARAM_CATE_ID = "cate_id";

  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;
  BackstageInCateAdapter mAdapter;
  private String mCateId;
  private int mPage = 1;

  public BackstageByCateFragment() {
    // Required empty public constructor
  }

  public static BackstageByCateFragment newInstance(String cateId) {
    BackstageByCateFragment fragment = new BackstageByCateFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM_CATE_ID, cateId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onFirstAppear() {
    setUpRecyclerView();
    fetchData();
  }

  private void fetchData() {
    Subscription subscription = ServiceGenerator
        .getVMovieService()
        .getBackstageList(mPage, 10, mCateId)
        .map(new ExtractDataFunc<List<Movie>>())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<Movie>>() {
          @Override
          public void call(List<Movie> movies) {
            if (movies != null) {
              mAdapter.addAll(movies);
            }
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            Logger.e(throwable, "get backstage list");
          }
        });
    addSubscription(subscription);
  }

  private void setUpRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(layoutManager);
    mAdapter = new BackstageInCateAdapter(getActivity(), R.layout.item_backstage);
    mAdapter.setOnItemClickListener(new CommonViewAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), BackstageDetailActivity.class);
        intent.putExtra(BackstageDetailActivity.DETAIL_URL,
            mAdapter.getDataItem(position).getRequest_url());
        intent.putExtra(BackstageDetailActivity.POST_ID, mAdapter.getDataItem(position).getPostid());
        startActivity(intent);
      }
    });
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    mRecyclerView.addOnScrollListener(new EndlessScrollListener() {
      @Override
      public void onLoadMore() {
        mPage++;
        fetchData();
      }
    });
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mCateId = getArguments().getString(ARG_PARAM_CATE_ID);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_backstage_by_cate, container, false);
  }
}
