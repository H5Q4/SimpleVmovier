package com.github.jupittar.vmovier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.jupittar.commlib.base.BaseActivity;
import com.github.jupittar.commlib.custom.recyclerview.EndlessScrollListener;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.adapters.MoviesInCateAdapter;
import com.github.jupittar.vmovier.models.Category;
import com.github.jupittar.vmovier.models.Movie;
import com.github.jupittar.vmovier.network.ExtractDataFunc;
import com.github.jupittar.vmovier.network.ServiceGenerator;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CategoryMoviesActivity extends BaseActivity {

    public static final String CATEGORY = "Category";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_tv)
    TextView mTitleTextView;
    @BindView(R.id.recycler_view)
    RecyclerView mMovieRecyclerView;

    private MoviesInCateAdapter mMoviesAdapter;
    private int mPage = 1;
    private Category mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_movies);
        mCategory = getIntent().getParcelableExtra(CATEGORY);
        setUpToolbar();
        setUpRecyclerView();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTitleTextView.setText(mCategory.getCatename());
    }

    private void setUpRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMovieRecyclerView.setLayoutManager(linearLayoutManager);
        mMovieRecyclerView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesInCateAdapter(this, R.layout.item_movie);
        mMoviesAdapter.setOnItemClickListener(new CommonViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Movie movie = mMoviesAdapter.getItemList().get(position);
                Intent intent = new Intent(CategoryMoviesActivity.this, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.POST_ID, movie.getPostid());
                intent.putExtra(MovieDetailActivity.WEB_VIEW_URL, movie.getRequest_url());
                startActivity(intent);
            }
        });
        mMovieRecyclerView.setAdapter(mMoviesAdapter);
        mMovieRecyclerView.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                mPage++;
                fetchMovies();
            }
        });
        fetchMovies();
    }

    private void fetchMovies() {
        String tab = mCategory.getTab();
        String cateId = mCategory.getCateid();
        Observer<List<Movie>> observer = new Observer<List<Movie>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e, "get category movies");
            }

            @Override
            public void onNext(List<Movie> movies) {
                if (movies != null) {
                    mMoviesAdapter.addAll(movies);
                }
            }
        };
        Subscription subscription = null;
        if (tab != null) {
            subscription = ServiceGenerator
                .getVMovieService()
                .getMoviesByTab(mPage, 20, tab)
                .map(new ExtractDataFunc<List<Movie>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        } else if (cateId != null) {
            subscription = ServiceGenerator
                .getVMovieService()
                .getMoviesInCate(mPage, 20, cateId)
                .map(new ExtractDataFunc<List<Movie>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        }
        addSubscription(subscription);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
