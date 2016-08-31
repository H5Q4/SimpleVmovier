package com.github.jupittar.vmovier.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.github.jupittar.commlib.base.SubscriptionActivity;
import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.commlib.rxbus.BusSubscriber;
import com.github.jupittar.commlib.rxbus.RxBus;
import com.github.jupittar.commlib.utilities.ToastUtils;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.adapters.ContentFragmentPageAdapter;
import com.github.jupittar.vmovier.fragments.BackstageFragment;
import com.github.jupittar.vmovier.fragments.ChannelsFragment;
import com.github.jupittar.vmovier.ui.home.HomeFragment;
import com.github.jupittar.vmovier.ui.series.SeriesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;

public class MainActivity extends SubscriptionActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EVENT_TITLE_CHANGE = "Toolbar_section_title_change";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.title_switcher)
    TextSwitcher mTitleSwitcher;
    @BindView(R.id.view_pager)
    SCViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setUpDrawer();
        setUpViewPager();
    }

    private void setUpViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.MENU_INDEX_HOME, new HomeFragment());
        fragments.add(ChannelsFragment.MENU_INDEX_CHANNEL, new ChannelsFragment());
        fragments.add(SeriesFragment.MENU_INDEX_SERIES, new SeriesFragment());
        fragments.add(BackstageFragment.MENU_INDEX_BACKSTAGE, new BackstageFragment());
        ContentFragmentPageAdapter adapter = new ContentFragmentPageAdapter(
            getSupportFragmentManager(),
            fragments
        );
        mViewPager.setAdapter(adapter);
        mViewPager.setScrollEnabled(false);
        mViewPager.setOffscreenPageLimit(fragments.size());
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mTitleSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView titleView = new TextView(MainActivity.this);
                TextViewCompat.setTextAppearance(titleView, android.R.style.TextAppearance_Medium);
                Typeface t = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/Lobster-Regular.ttf");
                titleView.setTypeface(t);
                titleView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                titleView.setGravity(Gravity.CENTER);
                return titleView;
            }
        });
        mTitleSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        mTitleSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
        mTitleSwitcher.setText(getString(R.string.common_latest));
        Subscription subscription = RxBus.getDefault().toObservable(EVENT_TITLE_CHANGE, String.class)
            .subscribe(new BusSubscriber<String>() {
                @Override
                protected void received(String s) {
                    TextView textView = (TextView) mTitleSwitcher.getCurrentView();
                    String str = textView.getText().toString();
                    if (!str.equals(s)) {
                        mTitleSwitcher.setText(s);
                    }
                }
            });
        addSubscription(subscription);
    }


    private void setUpDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private boolean mExit;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (mExit) {
                finish();
            }
            ToastUtils.showShort(this, "再按一次退出应用");
            mExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   mExit = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = "";
        int position = HomeFragment.MENU_INDEX_HOME;
        if (id == R.id.nav_home) {
            position = HomeFragment.MENU_INDEX_HOME;
            title = "Latest";
        } else if (id == R.id.nav_channel) {
            position = ChannelsFragment.MENU_INDEX_CHANNEL;
            title = "频道";
        } else if (id == R.id.nav_series) {
            position = SeriesFragment.MENU_INDEX_SERIES;
            title = "系列";
        } else if (id == R.id.nav_backstage) {
            position = BackstageFragment.MENU_INDEX_BACKSTAGE;
            title = "幕后";

        } else if (id == R.id.nav_share) {
            return true;

        } else if (id == R.id.nav_send) {
            return true;
        }

        mViewPager.setCurrentItem(position, false);
        mTitleSwitcher.setCurrentText(title);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
