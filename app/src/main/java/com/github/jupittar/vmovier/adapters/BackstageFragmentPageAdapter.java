package com.github.jupittar.vmovier.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.jupittar.vmovier.fragments.BackstageByCateFragment;
import com.github.jupittar.vmovier.models.BackstageCategory;

import java.util.List;

public class BackstageFragmentPageAdapter extends FragmentPagerAdapter {

  private List<BackstageCategory> mCategories;

  public BackstageFragmentPageAdapter(FragmentManager fm, List<BackstageCategory> categories) {
    super(fm);
    this.mCategories = categories;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mCategories.get(position).getCatename();
  }

  @Override
  public Fragment getItem(int position) {
    return BackstageByCateFragment.newInstance(mCategories.get(position).getCateid());
  }

  @Override
  public int getCount() {
    return mCategories.size();
  }
}
