package com.github.jupittar.vmovier;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.github.jupittar.commlib.utilities.ToastUtils;
import com.github.jupittar.vmovier.activities.BackstageDetailActivity;
import com.github.jupittar.vmovier.activities.MovieDetailActivity;
import com.github.jupittar.vmovier.activities.VideoPlayActivity;
import com.github.jupittar.vmovier.models.MovieDetail;

import java.util.List;

public class AndroidCalledByJs {

    private Context mContext;

    public AndroidCalledByJs(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void onClickRecommendMovie(String postId) {
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.POST_ID, postId);
        intent.putExtra(MovieDetailActivity.WEB_VIEW_URL,
            String.format("http://app.vmoiver.com/%s?qingapp=app_new", postId));
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void onClickMovieInBackstage(int index) {
        List<MovieDetail.Content.Video> videos = BackstageDetailActivity.mVideos;
        if (videos == null) {
            return;
        }
        Intent intent = new Intent(mContext, VideoPlayActivity.class);
        intent.putExtra(VideoPlayActivity.VIDEO_URL, videos.get(index).getQiniu_url());
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void showToast(String msg) {
        ToastUtils.showShort(mContext, msg);
    }
}
