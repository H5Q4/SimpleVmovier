package com.github.jupittar.vmovier.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.base.LazyFragment;
import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.adapters.BackstageFragmentPageAdapter;
import com.github.jupittar.vmovier.data.models.BackstageCategory;
import com.github.jupittar.vmovier.data.source.remote.ExtractDataFunc;
import com.github.jupittar.vmovier.data.source.remote.ServiceGenerator;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BackstageFragment extends LazyFragment {

    public static final int MENU_INDEX_BACKSTAGE = 3;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    SCViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_backstage, container, false);
    }

    @Override
    public void onFirstAppear() {
        fetchCategories();
    }

    private void fetchCategories() {
        Subscription subscription = ServiceGenerator
                .getVMovieService()
                .getBackstageCategories()
                .map(new ExtractDataFunc<List<BackstageCategory>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<BackstageCategory>>() {
                    @Override
                    public void call(List<BackstageCategory> categories) {
                        if (categories != null) {
                            setUpViewPager(categories);
                            setUpTabLayout();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable, "get backstage categories");
                    }
                });
        addSubscription(subscription);
    }

    private void setUpViewPager(List<BackstageCategory> categories) {
        mViewPager.setScrollEnabled(false);
        BackstageFragmentPageAdapter adapter = new BackstageFragmentPageAdapter(getChildFragmentManager(), categories);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(adapter.getCount());
    }

    private void setUpTabLayout(){
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
