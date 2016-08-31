package com.github.jupittar.vmovier.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.base.LazyFragment;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.activities.CategoryMoviesActivity;
import com.github.jupittar.vmovier.adapters.CategoryAdapter;
import com.github.jupittar.vmovier.models.Category;
import com.github.jupittar.vmovier.network.ExtractDataFunc;
import com.github.jupittar.vmovier.network.ServiceGenerator;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MovieCategoryFragment extends LazyFragment {

    @BindView(R.id.cate_grid_rv)
    RecyclerView mCateRecyclerView;

    private CategoryAdapter mCategoryAdapter;

    public MovieCategoryFragment(){}

    @Override
    public void onFirstAppear() {
        setUpCateRecyclerView();
    }

    private void setUpCateRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mCateRecyclerView.setLayoutManager(layoutManager);
        mCateRecyclerView.setHasFixedSize(true);
        mCategoryAdapter = new CategoryAdapter(getActivity(), R.layout.item_category);
        mCateRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemClickListener(new CommonViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Category category = mCategoryAdapter.getDataItem(position);
                Intent intent = new Intent(getActivity(), CategoryMoviesActivity.class);
                intent.putExtra(CategoryMoviesActivity.CATEGORY, category);
                startActivity(intent);
            }
        });
        fetchCategories();
    }

    private void fetchCategories() {
        ServiceGenerator
            .getVMovieService()
            .getCateList()
            .map(new ExtractDataFunc<List<Category>>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Category>>() {
                @Override
                public void call(List<Category> categories) {
                    if (categories != null){
                        mCategoryAdapter.addAll(categories);
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Logger.e(throwable, "getCateList");
                }
            });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_cate, container, false);
    }

}
