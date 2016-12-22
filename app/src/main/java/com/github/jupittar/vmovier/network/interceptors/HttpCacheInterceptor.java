package com.github.jupittar.vmovier.network.interceptors;

import android.content.Context;

import com.github.jupittar.commlib.utilities.NetworkUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpCacheInterceptor implements Interceptor {

  private Context mContext;

  public HttpCacheInterceptor(Context context) {
    mContext = context;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    if (!NetworkUtils.isNetworkConnected(mContext)) {
      request = request.newBuilder()
          .cacheControl(CacheControl.FORCE_CACHE)
          .build();
      Logger.e("Network Not Connected.");
    }
    Response response = chain.proceed(request);
    if (NetworkUtils.isNetworkConnected(mContext)) {
      int maxAge = 60 * 60;
      response = response.newBuilder()
          .removeHeader("Pragma")
          .header("Cache-Control", "public, max-age=" + maxAge)
          .build();
    } else {
      int maxStale = 60 * 60 * 24 * 28;
      response = response.newBuilder()
          .removeHeader("Pragma")
          .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
          .build();
      Logger.e("From cache.");
    }
    return response;
  }
}
