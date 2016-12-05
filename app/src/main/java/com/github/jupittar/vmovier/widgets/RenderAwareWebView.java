package com.github.jupittar.vmovier.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.github.lzyzsd.jsbridge.BridgeWebView;

public class RenderAwareWebView extends BridgeWebView {

  private BeginDisplayListener mListener;

  public RenderAwareWebView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setOnBeginDisplayListener(BeginDisplayListener listener) {
    mListener = listener;
  }

  @Override
  public void invalidate() {
    super.invalidate();

    if (getContentHeight() > 0) {
      if (mListener != null) {
        mListener.onBeginDisplay();
      }
    }
  }

  public interface BeginDisplayListener {
    void onBeginDisplay();
  }
}
