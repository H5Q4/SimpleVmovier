package com.github.jupittar.commlib.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class AspectRatioLayout extends RelativeLayout {

    public static final double SQUARE = 1.0D;
    public static final double PHI = 2 / (Math.sqrt(5) + 1);

    private double mAspectRatio;

    public void setAspectRatio(double ratio) {
        if (ratio != mAspectRatio) {
            mAspectRatio = ratio;
            requestLayout();
        }
    }

    public double getAspectRatio() {
        return mAspectRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAspectRatio > 0.0D) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mAspectRatio);
            super.onMeasure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            );
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public AspectRatioLayout(Context context) {
        super(context);
    }

    public AspectRatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
