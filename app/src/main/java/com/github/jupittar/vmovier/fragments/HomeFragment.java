package com.github.jupittar.vmovier.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.base.LazyFragment;
import com.github.jupittar.commlib.custom.AutoScrollPager;
import com.github.jupittar.commlib.custom.LoadingImageView;
import com.github.jupittar.commlib.custom.recyclerview.EndlessScrollListener;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.commlib.custom.recyclerview.entity.SectionedItem;
import com.github.jupittar.commlib.rxbus.RxBus;
import com.github.jupittar.commlib.utilities.SnackbarUtils;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.VMovierApplication;
import com.github.jupittar.vmovier.activities.MainActivity;
import com.github.jupittar.vmovier.activities.MovieDetailActivity;
import com.github.jupittar.vmovier.adapters.MoviesAdapter;
import com.github.jupittar.vmovier.models.Banner;
import com.github.jupittar.vmovier.models.Movie;
import com.github.jupittar.vmovier.network.ExtractDataFunc;
import com.github.jupittar.vmovier.network.ServiceGenerator;
import com.orhanobut.logger.Logger;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomeFragment extends LazyFragment {

    @BindView(R.id.reload_btn)
    Button mReloadButton;
    @BindView(R.id.error_ll)
    LinearLayout mErrorLayout;
    @BindView(R.id.loading_iv)
    LoadingImageView mLoadingImageView;
    @BindView(R.id.recycler_view)
    RecyclerView mMovieRecyclerView;

    AutoScrollPager mBanner;

    private int mPage = 1;
    private MoviesAdapter mMoviesAdapter;
    private DateFormat mDateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH);

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onFirstAppear() {
        setUpBanner();
        setUpMovieRecyclerView();
        mReloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorLayout.setVisibility(View.GONE);
                mLoadingImageView.setVisibility(View.VISIBLE);
                fetchBanner();
                fetchMovies();
            }
        });
    }

    private void setUpBanner() {
        LinearLayout banner = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.banner, null);
        mBanner = (AutoScrollPager) banner.findViewById(R.id.banner);
        banner.removeView(mBanner);
        fetchBanner();
    }

    private void fetchBanner() {
        Subscription subscription = ServiceGenerator.getVMovieService().getBanner()
            .map(new ExtractDataFunc<List<Banner>>())
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
                    mBanner.setImages(imageUrls, new AutoScrollPager.AutoScrollPagerListener() {
                        @Override
                        public void displayImage(String imgUrl, ImageView imageView) {
                            Glide.with(getActivity())
                                .load(imgUrl)
                                .centerCrop()
                                .into(imageView);
                        }

                        @Override
                        public void setTitle(int position, TextView textView) {

                        }

                        @Override
                        public void onImageClick(int position, ImageView imageView) {

                        }
                    });
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Logger.e(throwable, "get banner");
                }
            });
        addSubscription(subscription);
    }

    private void setUpMovieRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mMovieRecyclerView.setLayoutManager(linearLayoutManager);
        mMovieRecyclerView.setHasFixedSize(true);
        mMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMoviesAdapter = new MoviesAdapter(getActivity(), R.layout.item_movie, R.layout.item_section);
        mMoviesAdapter.addHeaderView(mBanner);
        mMoviesAdapter.setOnItemClickListener(new CommonViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    return;
                }
                position--;
                SectionedItem<Movie> item = mMoviesAdapter.getItemList().get(position);
                if (item.isHead()){
                    return;
                }
                String movieId = item.getItem().getPostid();
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.POST_ID, movieId);
                intent.putExtra(MovieDetailActivity.WEB_VIEW_URL, item.getItem().getRequest_url());
                startActivity(intent);
            }
        });
        mMovieRecyclerView.setAdapter(mMoviesAdapter);
        mMovieRecyclerView.addOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                if (position == 0) {
                    mBanner.startAutoPlay();
                    return;
                } else {
                    mBanner.stopAutoPlay();
                }
                if (mMoviesAdapter.hasHeader()) {
                    position--;
                }
                String title;
                if (dy < 0 && mMoviesAdapter.getItemList().get(position + 1).isHead()) {
                    Movie movie = mMoviesAdapter.getItemList().get(position - 1).getItem();
                    title = mDateFormat.format(new Date(Long.parseLong(movie.getPublish_time()) * 1000)).split(",")[0];
                    if (title.equals(mDateFormat.format(new Date(
                        Long.parseLong(mMoviesAdapter.getItemList().get(0).getItem().getPublish_time()) * 1000)).split(",")[0])) {
                        title = "Latest";
                    }
                    RxBus.getDefault().post(MainActivity.EVENT_TITLE_CHANGE, title);
                } else if (dy > 0 && mMoviesAdapter.getItemList().get(position).isHead()) {
                    title = mMoviesAdapter.getItemList().get(position).getTitle();
                    RxBus.getDefault().post(MainActivity.EVENT_TITLE_CHANGE, title);
                }
            }

            @Override
            public void onLoadMore() {
                mPage++;
                fetchMovies();
            }
        });

        fetchMovies();

    }

    private void fetchMovies() {
        Subscription getLatestMovies = ServiceGenerator.getVMovieService().getMoviesByTab(mPage, 20, "latest")
            .map(new ExtractDataFunc<List<Movie>>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Movie>>() {
                @Override
                public void call(List<Movie> movies) {
                    if (movies != null) {
                        List<SectionedItem<Movie>> sectionedItems = new ArrayList<>();
                        String lastTitle = "";
                        if (mMoviesAdapter.getItemCountIgnoreHF() > 0) {
                            Movie lastMovie = mMoviesAdapter.getItemList().get(mMoviesAdapter.getItemCountIgnoreHF() - 1).getItem();
                            lastTitle = mDateFormat.format(new Date(Long.parseLong(lastMovie.getPublish_time()) * 1000)).split(",")[0];
                        }
                        for (int i = 0; i < movies.size(); i++) {
                            String title = mDateFormat.format(new Date(Long.parseLong(movies.get(i).getPublish_time()) * 1000)).split(",")[0];
                            if (i == 0 && mMoviesAdapter.getItemCountIgnoreHF() == 0) {
                                lastTitle = title;
                            }
                            if (!title.equals(lastTitle)) {
                                sectionedItems.add(new SectionedItem<Movie>(true, title));
                                lastTitle = title;
                            }
                            sectionedItems.add(new SectionedItem<>(movies.get(i)));

                        }
                        mMoviesAdapter.addAll(sectionedItems);
                        mLoadingImageView.setVisibility(View.GONE);
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    if (throwable instanceof SocketTimeoutException
                        || throwable instanceof UnknownHostException
                        || throwable instanceof SocketException) {
                        if (mPage == 1) {
                            mErrorLayout.setVisibility(View.VISIBLE);
                        } else {
                            new SnackbarUtils.SnackbarBuilder()
                                .view(getActivity().findViewById(android.R.id.content))
                                .message("网络错误，加载失败")
                                .duration(Snackbar.LENGTH_INDEFINITE)
                                .backgroundColor(ContextCompat.getColor(getActivity(), R.color.material_blue_grey))
                                .messageColor(ContextCompat.getColor(getActivity(), R.color.material_amber_dark))
                                .actionText("重试")
                                .action(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        fetchMovies();
                                    }
                                })
                                .build()
                                .show();
                        }
                    }
                    mLoadingImageView.setVisibility(View.GONE);
                    Logger.e(throwable, "getLatestMovies");
                }
            });
        addSubscription(getLatestMovies);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VMovierApplication.shouldDie(this);
    }
}
