package com.github.jupittar.vmovier.ui.home;


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
import com.github.jupittar.vmovier.data.source.DataManager;
import com.github.jupittar.vmovier.ui.main.MainActivity;
import com.github.jupittar.vmovier.activities.MovieDetailActivity;
import com.github.jupittar.vmovier.adapters.MoviesAdapter;
import com.github.jupittar.vmovier.data.models.Movie;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class HomeFragment extends LazyFragment implements HomeMvp.View {

    public static final int MENU_INDEX_HOME = 0;
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
    private HomePresenter mHomePresenter;

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
                showErrorLayout();
                showLoading();
                mHomePresenter.fetchBanner();
                mHomePresenter.fetchMovies(mPage);
            }
        });
    }

    private void setUpBanner() {
        LinearLayout banner = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.banner, null);
        mBanner = (AutoScrollPager) banner.findViewById(R.id.banner);
        banner.removeView(mBanner);
        mHomePresenter.fetchBanner();
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
                if (item.isHead()) {
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
                if (dy < 0 && mMoviesAdapter.getDataItem(position + 1).isHead()) {
                    title = mHomePresenter.formatTitle(mMoviesAdapter.getDataItem(position - 1).getItem());
                    if (title.equals(mHomePresenter.formatTitle(mMoviesAdapter.getDataItem(0).getItem()))) {
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
                mHomePresenter.fetchMovies(mPage);
            }
        });

        mHomePresenter.fetchMovies(mPage);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHomePresenter = new HomePresenter(DataManager.getInstance());
        mHomePresenter.attachView(this);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomePresenter.detachView();
        VMovierApplication.shouldDie(this);
    }



    //region Implementation of HomeMvp.View
    @Override
    public int getMovieCount() {
        return mMoviesAdapter.getItemCountIgnoreHF();
    }

    @Override
    public SectionedItem<Movie> getLastMovie() {
        return mMoviesAdapter.getDataItem(getMovieCount() - 1);
    }

    @Override
    public void handleBanner(List<String> imageUrls) {
        mBanner.setImages(imageUrls, new AutoScrollPager.AutoScrollPagerListener() {
            @Override
            public void displayImage(String imgUrl, ImageView imageView) {
                Glide.with(imageView.getContext())
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

    @Override
    public void showMovies(List<SectionedItem<Movie>> movies) {
        mMoviesAdapter.addAll(movies);
    }

    @Override
    public void showLoading() {
        mLoadingImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoadingImageView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorLayout() {
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorSnackbar(final String msg) {
        new SnackbarUtils.SnackbarBuilder()
            .view(getActivity().findViewById(android.R.id.content))
            .message(msg)
            .duration(Snackbar.LENGTH_INDEFINITE)
            .backgroundColor(ContextCompat.getColor(getActivity(), R.color.material_blue_grey))
            .messageColor(ContextCompat.getColor(getActivity(), R.color.material_amber_dark))
            .actionText("重试")
            .action(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHomePresenter.fetchMovies(mPage);
                }
            })
            .build()
            .show();
    }
    //endregion

}
