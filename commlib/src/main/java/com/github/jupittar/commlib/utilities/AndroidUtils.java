package com.github.jupittar.commlib.utilities;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Field;

public class AndroidUtils {

  public static File getCacheDirectory(Context context) {
    return context.getCacheDir();
  }

  /**
   * 获取手机的硬件信息
   */
  public static String getDeviceInfo() {
    StringBuilder sb = new StringBuilder();
    //通过反射获取系统的硬件信息
    try {

      Field[] fields = Build.class.getDeclaredFields();
      for (Field field : fields) {
        //暴力反射 ,获取私有的信息
        field.setAccessible(true);
        String name = field.getName();
        String value = field.get(null).toString();
        sb.append(name).append("=").append(value);
        sb.append("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  /**
   * Hides keyboard
   */
  public static void hideKeyboard(Activity context) {
    View view = context.getCurrentFocus();
    if (view != null) {
      ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
          hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }
}
