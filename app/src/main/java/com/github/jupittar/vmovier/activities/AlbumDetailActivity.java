package com.github.jupittar.vmovier.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.jupittar.commlib.base.BaseActivity;
import com.github.jupittar.vmovier.R;
import com.github.lzyzsd.jsbridge.BridgeWebView;

import butterknife.BindView;

public class AlbumDetailActivity extends BaseActivity {

  public static final String ALBUM_ID = "album_id";

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.title_tv)
  TextView mTitleTv;
  @BindView(R.id.web_view)
  BridgeWebView mWebView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_album_detail);
    setUpToolbar();
    setUpWebView();
  }

  @SuppressLint("SetJavaScriptEnabled")
  private void setUpWebView() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      WebView.setWebContentsDebuggingEnabled(true);
    }
    WebSettings settings = mWebView.getSettings();
    settings.setJavaScriptEnabled(true);

  }

  private void setUpToolbar() {
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowHomeEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
    }
    mTitleTv.setText("专题");
  }
}
