package com.github.jupittar.vmovier.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.github.jupittar.commlib.base.BaseActivity;
import com.github.jupittar.vmovier.AndroidCalledByJs;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.Utils;
import com.github.jupittar.vmovier.models.MovieDetail;
import com.github.jupittar.vmovier.network.ExtractDataFunc;
import com.github.jupittar.vmovier.network.ServiceGenerator;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BackstageDetailActivity extends BaseActivity {

    public static final String POST_ID = "post_id";
    public static final String DETAIL_URL = "detail_url";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.title_tv)
    TextView mTitleTextView;
    public static List<MovieDetail.Content.Video> mVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backstage_detail);
        setUpToolbar();
        setUpWebView();
        fetchPostDetail();
    }

    private void fetchPostDetail() {
        String postId = getIntent().getStringExtra(POST_ID);
        if (postId != null) {
            Subscription subscription = ServiceGenerator.getVMovieService()
                .getMovieDetail(postId)
                .map(new ExtractDataFunc<MovieDetail>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MovieDetail>() {
                    @Override
                    public void call(MovieDetail movieDetail) {
                        if (movieDetail != null) {
                            mVideos = movieDetail.getContent().getVideo();
                            String url = getIntent().getStringExtra(DETAIL_URL);
                            if (url != null) {
                                mWebView.loadUrl(url);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable, "Get movie detail");
                    }
                });
            addSubscription(subscription);
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTitleTextView.setText("幕后文章");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Utils.loadLocalJs(view, "js/script.js");
            }
        });
        mWebView.addJavascriptInterface(new AndroidCalledByJs(this), "android");
        mWebView.setVerticalScrollBarEnabled(false);
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
