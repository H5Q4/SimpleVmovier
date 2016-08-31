package com.github.jupittar.vmovier.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.base.BaseActivity;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.commlib.rxbus.BusSubscriber;
import com.github.jupittar.commlib.rxbus.RxBus;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.adapters.SeriesFragmentPageAdapter;
import com.github.jupittar.vmovier.models.SeriesDetail;
import com.github.jupittar.vmovier.models.SeriesVideo;
import com.github.jupittar.vmovier.network.ExtractDataFunc;
import com.github.jupittar.vmovier.network.ServiceGenerator;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SeriesDetailActivity extends BaseActivity {

    public static final String SERIES_ID = "series_id";
    public static final String BUS_EVENT_CLICK_VIDEO = "bus_event_click_video";

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.video_thumbnail_iv)
    AspectRatioImageView mThumbnailImageView;
    @BindView(R.id.title_tv)
    TextView mTitleTextView;
    @BindView(R.id.info_tv)
    TextView mInfoTextView;
    @BindView(R.id.brief_tv)
    TextView mBriefTextView;
    @BindView(R.id.tag_tv)
    TextView mTagTextView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    SCViewPager mViewPager;
    @BindView(R.id.play_fab)
    FloatingActionButton mPlayButton;

    private String mVideoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);
        setUpToolbar();
        fetchSeriesDetail();
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoUrl != null) {
                    Intent intent = new Intent(SeriesDetailActivity.this, VideoPlayActivity.class);
                    intent.putExtra(VideoPlayActivity.VIDEO_URL, mVideoUrl);
                    startActivity(intent);
                }
            }
        });
        Subscription subscription = RxBus.getDefault()
            .toObservable(BUS_EVENT_CLICK_VIDEO, String.class)
            .subscribe(new BusSubscriber<String>() {
                @Override
                protected void received(String s) {
                    fetchSeriesVideo(s);
                    mPlayButton.performClick();
                }
            });
        addSubscription(subscription);
    }

    private void fetchSeriesVideo(String postId) {
        Subscription subscription = ServiceGenerator
            .getVMovieService()
            .getSeriesVideoById(postId)
            .map(new ExtractDataFunc<SeriesVideo>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<SeriesVideo>() {
                @Override
                public void call(SeriesVideo seriesVideo) {
                    if (seriesVideo != null) {
                        updateVideoInfo(seriesVideo);
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Logger.e(throwable, "Get series video by id");
                }
            });
        addSubscription(subscription);
    }

    private void setUpViewPager(SeriesDetail detail) {
        mViewPager.setScrollEnabled(false);
        mViewPager.setWrapContent(true);
        List<SeriesDetail.Posts> posts = detail.getPosts();
        SeriesFragmentPageAdapter pageAdapter = new SeriesFragmentPageAdapter(getSupportFragmentManager(), posts);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOffscreenPageLimit(pageAdapter.getCount());
    }

    private void setUpTabLayout() {
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

    private void fetchSeriesDetail() {
        String seriesId = getIntent().getStringExtra(SERIES_ID);
        Subscription subscription = ServiceGenerator
            .getVMovieService()
            .getSeriesDetail(seriesId)
            .map(new ExtractDataFunc<SeriesDetail>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<SeriesDetail>() {
                @Override
                public void call(SeriesDetail seriesDetail) {
                    if (seriesDetail != null) {
                        setUpSeriesInfo(seriesDetail);
                        Glide.with(SeriesDetailActivity.this)
                            .load(seriesDetail.getImage())
                            .centerCrop()
                            .into(mThumbnailImageView);
                        mCollapsingToolbarLayout.setTitle(seriesDetail.getTitle());

                        setUpViewPager(seriesDetail);
                        setUpTabLayout();
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Logger.e(throwable, "get series detail");
                }
            });
        addSubscription(subscription);
    }

    private void updateVideoInfo(SeriesVideo data) {
        mTitleTextView.setText(String.format("第%s集 %s", data.getEpisode(), data.getTitle()));
        mVideoUrl = data.getQiniu_url();
    }

    private void setUpSeriesInfo(SeriesDetail detail) {
        SeriesDetail.Posts posts = detail.getPosts().get(0);
        SeriesDetail.Posts.PostList list = posts.getList().get(0);
        fetchSeriesVideo(list.getSeries_postid());
        mTagTextView.setText(detail.getTitle());
        mInfoTextView.setText(String.format("更新: %s\n集数: 更新至%s集\n%s",
            detail.getWeekly(), detail.getUpdate_to(),
            TextUtils.isEmpty(detail.getTag_name()) ? "" : "类型: " + detail.getTag_name()));
        mBriefTextView.setText(detail.getContent());
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mThumbnailImageView.setAspectRatio(3.0D / 4.0D);
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(),
            "fonts/Lobster-Regular.ttf"));
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
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
