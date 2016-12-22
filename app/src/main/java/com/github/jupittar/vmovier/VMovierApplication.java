package com.github.jupittar.vmovier;

import android.app.Application;

import com.github.jupittar.vmovier.network.ServiceGenerator;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class VMovierApplication extends Application {

  private static final String LOGGER_TAG = "VMOVIERLOG";

  private static RefWatcher mRefWatcher;

  public static void shouldDie(Object object) {
    if (mRefWatcher != null) {
      mRefWatcher.watch(object);
    }
  }

  @Override
  public void onCreate() {
    super.onCreate();
    ServiceGenerator.init(this);
    initLogger();
    mRefWatcher = LeakCanary.install(this);
  }

  private void initLogger() {
    if (BuildConfig.DEBUG) {
      Logger
          .init(LOGGER_TAG)
          .methodCount(3)                 // default 2
          .logLevel(LogLevel.FULL)        // default LogLevel.FULL
          .hideThreadInfo()
          .methodOffset(2);                // default 0
    } else {
      Logger
          .init()
          .logLevel(LogLevel.NONE);
    }

  }

}
