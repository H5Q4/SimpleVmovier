package com.github.jupittar.vmovier.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.vmovier.fragments.MovieInSeriesFragment;
import com.github.jupittar.vmovier.models.SeriesDetail;

import java.util.List;

public class SeriesFragmentPageAdapter extends FragmentPagerAdapter {

  private List<SeriesDetail.Posts> mPostsList;
  private int mCurrentPosition = -1;

  public SeriesFragmentPageAdapter(FragmentManager fm, List<SeriesDetail.Posts> postsList) {
    super(fm);
    this.mPostsList = postsList;
  }

  @Override
  public void setPrimaryItem(ViewGroup container, int position, Object object) {
    super.setPrimaryItem(container, position, object);
    if (position != mCurrentPosition) {
      Fragment fragment = (Fragment) object;
      SCViewPager pager = (SCViewPager) container;
      if (fragment != null && fragment.getView() != null) {
        mCurrentPosition = position;
        pager.measureCurrentView(fragment.getView());
      }
    }
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mPostsList.get(position).getFrom_to();
  }

  @Override
  public Fragment getItem(int position) {
    return MovieInSeriesFragment.newInstance(mPostsList.get(position));
  }

  @Override
  public int getCount() {
    return mPostsList.size();
  }
}
