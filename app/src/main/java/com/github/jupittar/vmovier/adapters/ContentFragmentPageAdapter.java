package com.github.jupittar.vmovier.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ContentFragmentPageAdapter extends FragmentPagerAdapter {

  private List<Fragment> mFragments;

  public ContentFragmentPageAdapter(FragmentManager fm, @NonNull List<Fragment> fragments) {
    super(fm);
    this.mFragments = fragments;
  }

  @Override
  public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override
  public int getCount() {
    return mFragments.size();
  }
}
