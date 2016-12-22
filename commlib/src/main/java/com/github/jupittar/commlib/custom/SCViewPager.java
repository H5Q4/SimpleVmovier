package com.github.jupittar.commlib.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * A subclass of ViewPager which can be disable scrolling and wrap content.
 */
public class SCViewPager extends ViewPager {

  private boolean mEnableScroll;
  private boolean mWrapContent;
  private View mCurrentView;

  public SCViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.mEnableScroll = true;
    this.mWrapContent = false;
  }

  public void setWrapContent(boolean mWrapContent) {
    this.mWrapContent = mWrapContent;
    requestLayout();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (!mWrapContent || mCurrentView == null) {
      return;
    }
    int height = 0;
    mCurrentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    int h = mCurrentView.getMeasuredHeight();
    if (h > height) {
      height = h;
    }

    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return this.mEnableScroll && super.onTouchEvent(event);

  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return this.mEnableScroll && super.onInterceptTouchEvent(event);

  }

  public void setScrollEnabled(boolean enabled) {
    this.mEnableScroll = enabled;
  }

  public void measureCurrentView(View currentView) {
    mCurrentView = currentView;
    requestLayout();
  }

}
