package com.github.jupittar.vmovier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.github.jupittar.commlib.base.SubscriptionActivity;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.ui.main.MainActivity;

import butterknife.BindView;

public class SplashActivity extends SubscriptionActivity {

    @BindView(R.id.bg_iv)
    ImageView mSplashImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashImageView.startAnimation(animation);
    }

}
