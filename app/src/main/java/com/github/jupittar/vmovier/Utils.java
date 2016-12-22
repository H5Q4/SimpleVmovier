package com.github.jupittar.vmovier;

import android.webkit.WebView;

import com.github.jupittar.commlib.utilities.StringUtils;

public class Utils {

  public static void loadLocalJs(WebView view, String path) {
    String jsContent = StringUtils.assetFile2Str(view.getContext(), path);
    view.loadUrl("javascript:(function() {" +
        "var parent = document.getElementsByTagName('head').item(0);" +
        "var script = document.createElement('script');" +
        "script.type = 'text/javascript';" +
        // Tell the browser to BASE64-decode the string into your script !!!
        "script.innerHTML = " + jsContent + ";" +
        "parent.appendChild(script)" +
        "})()");
  }

}
