package com.github.jupittar.vmovier.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.jupittar.commlib.base.BaseActivity;
import com.github.jupittar.commlib.utilities.GsonUtils;
import com.github.jupittar.vmovier.R;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.reflect.TypeToken;


import java.util.Map;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    public static final String KEY_URL = "page_url";
    private static final int TYPE_MOVIE = 0;
    private static final int TYPE_WEB_PAGE = 1;
    private static final int TYPE_SERIES = 2;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_view)
    BridgeWebView mWebView;
    @BindView(R.id.title_tv)
    TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setUpToolbar();
        setUpWebView();
        String url = getIntent().getStringExtra(KEY_URL);
        if (url != null) {
            mWebView.loadUrl(url);
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebView.registerHandler("handlerPlayer", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (TextUtils.isEmpty(data)) {
                    return;
                }
                Map<String, String> dataMap = GsonUtils.toMap(data,
                    new TypeToken<Map<String, String>>() {
                    }.getType());
//                    String title = dataMap.get("title");
                String url = dataMap.get("url");
                Bundle bundle = new Bundle();
                bundle.putString(VideoPlayActivity.VIDEO_URL, url);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(WebViewActivity.this, VideoPlayActivity.class);
                startActivity(intent);
                function.onCallBack("{'status':'0'}");
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
                switch (Integer.parseInt(type)) {
                    case TYPE_MOVIE:
                        bundle.putString(MovieDetailActivity.POST_ID, id);
                        intent.putExtras(bundle);
                        intent.setClass(WebViewActivity.this, MovieDetailActivity.class);
                        startActivity(intent);
                        break;
                    case TYPE_WEB_PAGE:
                        bundle.putString(WebViewActivity.KEY_URL,
                            String.format("http://www.xinpianchang.com/u%s?qingapp=app_new",
                                id));
                        intent.putExtras(bundle);
                        intent.setClass(WebViewActivity.this, WebViewActivity.class);
                        startActivity(intent);
                        break;
                    case TYPE_SERIES:
                        bundle.putString(SeriesDetailActivity.SERIES_ID, id);
                        intent.putExtras(bundle);
                        intent.setClass(WebViewActivity.this, SeriesDetailActivity.class);
                        startActivity(intent);
                        break;
                }
                function.onCallBack("{'status':'0'}");
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitleTextView.setText(title);
            }
        });
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
