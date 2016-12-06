package com.github.jupittar.vmovier.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.base.BaseActivity;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.custom.LoadingImageView;
import com.github.jupittar.commlib.utilities.GsonUtils;
import com.github.jupittar.vmovier.AndroidCalledByJs;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.Utils;
import com.github.jupittar.vmovier.models.MovieDetail;
import com.github.jupittar.vmovier.network.ExtractDataFunc;
import com.github.jupittar.vmovier.network.ServiceGenerator;
import com.github.jupittar.vmovier.widgets.RenderAwareWebView;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.Map;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MovieDetailActivity extends BaseActivity {

  public static final String POST_ID = "post_id";
  private static final int TYPE_CLICK_PLAY = 0;
  private static final int TYPE_CLICK_DOWNLOAD = 1;

  @BindView(R.id.collapsing_toolbar)
  CollapsingToolbarLayout mCollapsingToolbarLayout;
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.web_view)
  RenderAwareWebView mWebView;
  @BindView(R.id.video_thumbnail_iv)
  AspectRatioImageView mThumbnailImageView;
  @BindView(R.id.play_fab)
  FloatingActionButton mPlayButton;
  @BindView(R.id.loading_iv)
  LoadingImageView mLoadingImageView;
  @BindView(R.id.reload_btn)
  Button mReloadButton;
  @BindView(R.id.error_ll)
  LinearLayout mErrorLayout;
  @BindView(R.id.main_content)
  View mContentMain;

  private String mVideoUrl;
  private String mPostId;
  private MovieDetail mMovieDetail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    mPostId = getIntent().getStringExtra(POST_ID);
    setUpToolbar();
    setUpWebView();
    fetchMovieDetail();
    mPlayButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startPlaying(mVideoUrl);
      }
    });
    mReloadButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mErrorLayout.setVisibility(View.GONE);
        mContentMain.setVisibility(View.VISIBLE);
        mLoadingImageView.setVisibility(View.VISIBLE);
        fetchMovieDetail();
      }
    });
  }

  private void startPlaying(String videoUrl) {
    if (videoUrl == null) {
      return;
    }
    Intent intent = new Intent(MovieDetailActivity.this, VideoPlayActivity.class);
    intent.putExtra(VideoPlayActivity.VIDEO_URL, videoUrl);
    startActivity(intent);
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
    mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this,
        android.R.color.transparent));
    mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this,
        android.R.color.white));
  }

  private void fetchMovieDetail() {
    ServiceGenerator.getVMovieService().getMovieDetail(mPostId)
        .map(new ExtractDataFunc<MovieDetail>())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<MovieDetail>() {
          @Override
          public void call(MovieDetail movieDetail) {
            mMovieDetail = movieDetail;
            Glide.with(MovieDetailActivity.this)
                .load(movieDetail.getImage())
                .centerCrop()
                .into(mThumbnailImageView);
            mWebView.loadUrl(String.format(
                "http://app.vmoiver.com/%s?qingapp=app_new",
                mPostId));
            mCollapsingToolbarLayout.setTitle(movieDetail.getTitle());
            mVideoUrl = movieDetail.getContent().getVideo().get(0).getQiniu_url();
            mPlayButton.setVisibility(View.VISIBLE);
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            mContentMain.setVisibility(View.GONE);
            mLoadingImageView.setVisibility(View.GONE);
            mErrorLayout.setVisibility(View.VISIBLE);
            Logger.e(throwable, "getMovieDetail");
          }
        });
  }

  @SuppressLint("SetJavaScriptEnabled")
  private void setUpWebView() {
    mWebView.setVisibility(View.INVISIBLE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      WebView.setWebContentsDebuggingEnabled(true);
    }
    WebSettings webSettings = mWebView.getSettings();

    webSettings.setJavaScriptEnabled(true);
    webSettings.setAllowUniversalAccessFromFileURLs(true);
    mWebView.setWebViewClient(new BridgeWebViewClient(mWebView) {

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Utils.loadLocalJs(view, "js/script.js");
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            mWebView.setVisibility(View.VISIBLE);
          }
        }, 200);
      }
    });
    mWebView.setOnBeginDisplayListener(new RenderAwareWebView.BeginDisplayListener() {
      @Override
      public void onBeginDisplay() {
        if (mLoadingImageView != null) {
          mLoadingImageView.setVisibility(View.GONE);
        }
      }
    });
    mWebView.registerHandler("handlerVideo", new BridgeHandler() {
      @Override
      public void handler(String data, CallBackFunction function) {
        if (TextUtils.isEmpty(data)) {
          return;
        }
        Map<String, String> dataMap = GsonUtils.toMap(data,
            new TypeToken<Map<String, String>>() {
            }.getType());
        String type = dataMap.get("type");
        String videoIdx = dataMap.get("videoIdx");
        if (Integer.valueOf(type) == TYPE_CLICK_PLAY) {
          if (Integer.valueOf(videoIdx) < mMovieDetail.getContent().getVideo().size()) {
            String videoUrl = mMovieDetail.getContent().getVideo().get(Integer.valueOf(videoIdx))
                .getQiniu_url();
            startPlaying(videoUrl);
          }
        } else if (Integer.valueOf(videoIdx) == TYPE_CLICK_DOWNLOAD) {
          //TODO download this video
        }
      }
    });
    mWebView.registerHandler("handlerNewView", new BridgeHandler() {
      @Override
      public void handler(String data, CallBackFunction function) {
        if (TextUtils.isEmpty(data)) {
          return;
        }
        Map<String, String> dataMap = GsonUtils.toMap(data,
            new TypeToken<Map<String, String>>() {
            }.getType());
        String type = dataMap.get("type");
        String id = dataMap.get("id");
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        switch (type) {
          case "0":
            bundle.putString(POST_ID, id);
            intent.setClass(MovieDetailActivity.this, MovieDetailActivity.class);
            break;
          case "1":
            bundle.putString(WebViewActivity.KEY_URL,
                String.format("http://www.xinpianchang.com/u%s?qingapp=app_new", id));
            intent.setClass(MovieDetailActivity.this, WebViewActivity.class);
            break;
          case "2":
            bundle.putString(SeriesDetailActivity.SERIES_ID, id);
            intent.setClass(MovieDetailActivity.this, SeriesDetailActivity.class);
            break;
        }
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });
    mWebView.registerHandler("handlerNewViewByUrl", new BridgeHandler() {
      @Override
      public void handler(String data, CallBackFunction function) {
        if (TextUtils.isEmpty(data)) {
          return;
        }
        Map<String, String> dataMap = GsonUtils.toMap(data,
            new TypeToken<Map<String, String>>() {
            }.getType());
        String url = dataMap.get("url");
        Bundle bundle = new Bundle();
        bundle.putString(WebViewActivity.KEY_URL, url);
        Intent intent = new Intent(MovieDetailActivity.this, WebViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.movie_detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == android.R.id.home) {
      finish();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

}
