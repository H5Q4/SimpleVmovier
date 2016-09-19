package com.github.jupittar.vmovier.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.devbrackets.android.exomedia.EMVideoView;
import com.github.jupittar.commlib.base.BaseActivity;
import com.github.jupittar.vmovier.R;

import butterknife.BindView;

public class VideoPlayActivity extends BaseActivity implements MediaPlayer.OnPreparedListener {

    public static final String VIDEO_URL = "video_url";

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.em_video_view) EMVideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setUpToolbar();
        setUpVideoView();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpVideoView() {
        mVideoView.setOnPreparedListener(this);
        String videoUrl = getIntent().getStringExtra(VIDEO_URL);
        mVideoView.setVideoURI(Uri.parse(videoUrl));
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //Starts the video playback as soon as it is ready
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        }
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
