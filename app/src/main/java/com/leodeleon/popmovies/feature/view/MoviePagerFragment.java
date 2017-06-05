package com.leodeleon.popmovies.feature.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.astuetz.PagerSlidingTabStrip;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.feature.MainActivity;

import static com.leodeleon.popmovies.feature.view.MoviesFragment.POSITION_POPULAR;
import static com.leodeleon.popmovies.feature.view.MoviesFragment.POSITION_RATED;

public class MoviePagerFragment extends Fragment {


  private static final int PAGE_GOUNT = 3;
  @BindView(R.id.viewpager) ViewPager mViewPager;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.tabs) PagerSlidingTabStrip mTabs;

  @BindString(R.string.popular) String popular;
  @BindString(R.string.sort_rated) String rated;
  @BindString(R.string.favorites) String favorites;

  private PagerAdapter adapter;
  private Unbinder unbinder;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_pager, container, false);
    unbinder = ButterKnife.bind(this , view);
    adapter = new PagerAdapter(getFragmentManager());
    mViewPager.setOffscreenPageLimit(PAGE_GOUNT - 1);
    mViewPager.setAdapter(adapter);
    mTabs.setViewPager(mViewPager);
    ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }


  class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return MoviesFragment.newInstance(position);
    }

    @Override
    public int getCount() {
      return PAGE_GOUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
      if (position == POSITION_POPULAR) {
        return popular;
      } else if (position == POSITION_RATED) {
        return rated;
      } else  {
        return favorites;
      }
    }
  }
}
