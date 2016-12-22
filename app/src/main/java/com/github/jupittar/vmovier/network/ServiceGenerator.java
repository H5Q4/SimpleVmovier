package com.github.jupittar.vmovier.network;

import android.content.Context;

import com.github.jupittar.commlib.utilities.AndroidUtils;
import com.github.jupittar.vmovier.BuildConfig;
import com.github.jupittar.vmovier.network.interceptors.HttpCacheInterceptor;
import com.github.jupittar.vmovier.network.interceptors.HttpLoggingInterceptor;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

  private static final String API_BASE_URL = "http://app.vmoiver.com/";
  private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;

  private static VMovieService sVMovieService;

  private ServiceGenerator() {

  }

  public static VMovieService getVMovieService() {
    return sVMovieService;
  }

  public static void init(Context context) {
    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    okHttpClientBuilder
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS);

    //set logging
    if (BuildConfig.DEBUG) {
      okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor());
    }

    //set cache interceptor
    okHttpClientBuilder.addNetworkInterceptor(new HttpCacheInterceptor(context));

    //install disk cache
    try {
      File cacheDir = new File(AndroidUtils.getCacheDirectory(context), "http");
      Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
      okHttpClientBuilder.cache(cache);
    } catch (Exception e) {
      Logger.e(e, "Unable to install disk cache.");
    }

    OkHttpClient okHttpClient = okHttpClientBuilder.build();
    Retrofit.Builder builder = new Retrofit.Builder();

    builder
        .baseUrl(API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    sVMovieService = builder.build().create(VMovieService.class);
  }

}
