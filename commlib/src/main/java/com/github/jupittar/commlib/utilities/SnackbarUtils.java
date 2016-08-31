package com.github.jupittar.commlib.utilities;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class SnackbarUtils {

    public static class SnackbarBuilder {
        private View mView;
        private CharSequence mMessage;
        private Drawable mBackgroundDrawable;
        @ColorInt
        private int mBackgroundColor;
        @ColorInt
        private int mMessageColor;
        @ColorInt
        private int mActionColor;
        private CharSequence mActionText;
        private int mDuration = Snackbar.LENGTH_SHORT;
        private View.OnClickListener mOnClickListener;

        public SnackbarBuilder() {
        }

        public SnackbarBuilder actionText(CharSequence text) {
            this.mActionText = text;
            return this;
        }

        public SnackbarBuilder action(View.OnClickListener listener) {
            this.mOnClickListener = listener;
            return this;
        }

        public SnackbarBuilder view(View view) {
            this.mView = view;
            return this;
        }

        public SnackbarBuilder message(CharSequence message) {
            this.mMessage = message;
            return this;
        }

        public SnackbarBuilder backgroundDrawable(Drawable drawable) {
            this.mBackgroundDrawable = drawable;
            return this;
        }

        public SnackbarBuilder backgroundColor(@ColorInt int color) {
            this.mBackgroundColor = color;
            return this;
        }

        public SnackbarBuilder messageColor(@ColorInt int color) {
            this.mMessageColor = color;
            return this;
        }

        public SnackbarBuilder actionColor(@ColorInt int color) {
            this.mActionColor = color;
            return this;
        }

        public SnackbarBuilder duration(int duration) {
            this.mDuration = duration;
            return this;
        }

        public Snackbar build() {
            SpannableStringBuilder ssb= new SpannableStringBuilder()
                .append(mMessage);
            if (mMessageColor != 0) {
                ssb.setSpan(
                    new ForegroundColorSpan(mMessageColor),
                    0,
                    mMessage.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            Snackbar snackbar = Snackbar.make(mView, ssb, mDuration);
            Snackbar.SnackbarLayout sbView = (Snackbar.SnackbarLayout) snackbar.getView();
            if (mBackgroundDrawable != null) {
                sbView.setBackground(mBackgroundDrawable);
            }
            if (mBackgroundColor != 0) {
                sbView.setBackgroundColor(mBackgroundColor);
            }
            if (mActionColor != 0) {
                snackbar.setActionTextColor(mActionColor);
            }

            if (mOnClickListener != null) {
                snackbar.setAction(mActionText, mOnClickListener);
            }
            return snackbar;
        }
    }

}
