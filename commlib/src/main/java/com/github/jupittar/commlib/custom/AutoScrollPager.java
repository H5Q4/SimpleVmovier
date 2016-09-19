package com.github.jupittar.commlib.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jupittar.commlib.R;

import java.util.ArrayList;
import java.util.List;

public class AutoScrollPager extends RelativeLayout {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 呈现图片轮播的ViewPager
     */
    private ViewPager mViewPager;

    /**
     * 图片轮播的底部容器
     */
    private RelativeLayout mBottomContainer;

    /**
     * 指示器父布局
     */
    private LinearLayout mIndicatorContainer;

    /**
     * 指示器的单个视图
     */
    private ImageView mIndicator;

    /**
     * 指示器的集合
     */
    private ImageView[] mIndicators;

    /**
     * 图片的title控件
     */
    private TextView mTitleLabel;

    private MyPagerAdapter mAdapter;

    private int mCurrentIndex = 0;

    /**
     * 是否自动轮播标识变量
     */
    private boolean mIsAutoPlay = true;

    /**
     * 屏幕密度
     */
    private float mScale;

    private int mBottomHeight = 48;

    private int mStartIndex = 0;

    private
    @ColorInt
    int mBottomBackgroundColor = android.R.color.transparent;

    private
    @DrawableRes
    int mFocusedIndicatorDrawable = R.drawable.indicator_focused;

    private
    @DrawableRes
    int mNormalIndicatorDrawable = R.drawable.indicator_normal;

    private android.os.Handler mHandler = new android.os.Handler();

    private Runnable mAutoPlayRunnable = new Runnable() {
        @Override
        public void run() {
            mViewPager.setCurrentItem(++mCurrentIndex);
        }
    };

    public AutoScrollPager(Context context) {
        this(context, null);
    }

    public AutoScrollPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollPager);
        int indexCount = typedArray.getIndexCount();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScale = dm.density;
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.AutoScrollPager_bottomHeight) {
                mBottomHeight = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    mBottomHeight, dm));

            } else if (attr == R.styleable.AutoScrollPager_bottomBackgroundColor) {
                mBottomBackgroundColor = typedArray.getColor(attr, mBottomBackgroundColor);

            } else if (attr == R.styleable.AutoScrollPager_startIndex) {
                mStartIndex = typedArray.getInt(attr, mStartIndex);
            }
        }
        typedArray.recycle();
        layoutViewPager();
        layoutBottomContainer();

        mCurrentIndex = mStartIndex;
    }

    /**
     * 设置图片轮播器的图片
     */
    public void setImages(final List<String> imgUrls, final AutoScrollPagerListener listener) {
        mBottomContainer.removeAllViews();

        mTitleLabel = new TextView(mContext);
        mTitleLabel.setTextColor(Color.WHITE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
            mBottomHeight);
        layoutParams.setMargins((int) (mScale * 3 + 0.5f), 0, 0, 0);
        mTitleLabel.setGravity(Gravity.CENTER_VERTICAL);
        layoutParams.addRule(ALIGN_PARENT_LEFT);
        mBottomContainer.addView(mTitleLabel, layoutParams);

        mIndicatorContainer = new LinearLayout(mContext);
        mIndicatorContainer.setOrientation(LinearLayout.HORIZONTAL);
        final int count = imgUrls.size();
        mIndicators = new ImageView[count];
        for (int i = 0; i < count; i++) {
            mIndicator = new ImageView(mContext);
            int widthParams = (int) (mScale * 18 + 0.5f);// XP与DP转换，适应不同分辨率
            int heightParams = (int) (mScale * 4 + 0.5f);
            int imageMargin = (int) (mScale * 3 + 0.5f);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widthParams,
                heightParams);
            lp.setMargins(imageMargin, imageMargin, imageMargin, imageMargin);
            lp.gravity = Gravity.CENTER_VERTICAL;
            mIndicators[i] = mIndicator;
            if (i == 0) {
                mIndicator.setBackgroundResource(mFocusedIndicatorDrawable);
            } else {
                mIndicator.setBackgroundResource(mNormalIndicatorDrawable);
            }
            mIndicatorContainer.addView(mIndicator, lp);
        }
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
            mBottomHeight);
        params.addRule(CENTER_IN_PARENT);
        mBottomContainer.addView(mIndicatorContainer, params);

        mAdapter = new MyPagerAdapter(imgUrls, listener);
        mViewPager.setAdapter(mAdapter);
        listener.setTitle(mStartIndex, mTitleLabel);

        //为viewpager设置监听器
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                int realPos = convertToRealPosition(position);
                for (int i = 0; i < mIndicators.length; i++) {
                    if (realPos == i) {
                        mIndicators[i].setBackgroundResource(mFocusedIndicatorDrawable);
                    } else {
                        mIndicators[i].setBackgroundResource(mNormalIndicatorDrawable);
                    }
                }
                listener.setTitle(realPos, mTitleLabel);
                startAutoPlay();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //设置触碰事件，手指离开前停止自动轮播
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        startAutoPlay();
                        break;
                    default:
                        stopAutoPlay();
                        break;
                }
                return false;
            }
        });

        mViewPager.setCurrentItem(mStartIndex + mIndicators.length * 1000);
        startAutoPlay();
    }

    public boolean isAutoPlay() {
        return mIsAutoPlay;
    }

    public void setAutoPlay(boolean isAutoPlay) {
        mIsAutoPlay = isAutoPlay;
    }

    public void startAutoPlay() {
        stopAutoPlay();
        if (mIsAutoPlay) {
            //todo extract as member
            mHandler.postDelayed(mAutoPlayRunnable, 5000);
        }
    }

    public void stopAutoPlay() {
        mHandler.removeCallbacks(mAutoPlayRunnable);
    }


    /**
     * 布局底部view
     */
    private void layoutBottomContainer() {
        mBottomContainer = new RelativeLayout(mContext);
        mBottomContainer.setBackgroundColor(ContextCompat.getColor(mContext, mBottomBackgroundColor));
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, mBottomHeight);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        addView(mBottomContainer, layoutParams);
    }

    /**
     * 布局ViewPager
     */
    private void layoutViewPager() {
        mViewPager = new ViewPager(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(layoutParams);
        addView(mViewPager);
    }

    /**
     * 转换为真实position
     */
    private int convertToRealPosition(int position) {
        return position % mIndicators.length;
    }

    /**
     * callback
     */
    public interface AutoScrollPagerListener {

        /**
         * 加载图片
         */
        void displayImage(String imgUrl, ImageView imageView);

        /**
         * 设置图片标题
         */
        void setTitle(int position, TextView textView);

        /**
         * 响应图片的点击事件
         */
        void onImageClick(int position, ImageView imageView);

    }

    /**
     * adapter
     */
    private class MyPagerAdapter extends PagerAdapter {

        private List<String> mImgUrls;

        private List<ImageView> mCachedImageViews;

        private AutoScrollPagerListener mListener;

        private MyPagerAdapter(List<String> imgUrls, AutoScrollPagerListener listener) {
            mImgUrls = imgUrls;
            mListener = listener;
            mCachedImageViews = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final int realPos = convertToRealPosition(position);
            String imgUrl = mImgUrls.get(realPos);
            ImageView imageView;
            if (mCachedImageViews.isEmpty()) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = mCachedImageViews.remove(0);
            }

            mListener.displayImage(imgUrl, imageView);
            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mListener.onImageClick(realPos, (ImageView) v);
                }
            });
//            imageView.setTag(imgUrl);
            container.addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView imageView = (ImageView) object;
            container.removeView(imageView);
            mCachedImageViews.add(imageView);
        }
    }


}
