package com.github.jupittar.commlib.utilities;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class ToastUtils {

    public static void showLong(Context ctx, String content) {
        Toast.makeText(ctx, content, Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context ctx, String content) {
        Toast.makeText(ctx, content, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context ctx, @StringRes int resId) {
        Toast.makeText(ctx, resId, Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context ctx, @StringRes int resId) {
        Toast.makeText(ctx, resId, Toast.LENGTH_SHORT).show();
    }
}
