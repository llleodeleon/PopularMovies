package com.leodeleon.popmovies.feature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.astuetz.PagerSlidingTabStrip;
import com.leodeleon.popmovies.R;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

import static com.leodeleon.popmovies.feature.MoviesFragment.POSITION_FAVORITE;
import static com.leodeleon.popmovies.feature.MoviesFragment.POSITION_POPULAR;
import static com.leodeleon.popmovies.feature.MoviesFragment.POSITION_RATED;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
  @Inject DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

  private static final String TAG = "MainActivity";
  private static final int PAGE_GOUNT = 3;
  @BindView(R.id.viewpager) ViewPager mViewPager;
  @BindView(R.id.toolbar)
  public Toolbar mToolbar;
  @BindView(R.id.tabs) PagerSlidingTabStrip mTabs;

  @BindString(R.string.popular)
  String popular;
  @BindString(R.string.sort_rated)
  String rated;

  @BindString(R.string.favorites)
  String favorites;

  private PagerAdapter adapter;

  private Unbinder unbinder;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    adapter = new PagerAdapter(getSupportFragmentManager());
    mViewPager.setOffscreenPageLimit(PAGE_GOUNT - 1);
    mViewPager.setAdapter(adapter);
    mTabs.setViewPager(mViewPager);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @Override public AndroidInjector<Fragment> supportFragmentInjector() {
    return dispatchingAndroidInjector;
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
      switch (position) {
        case POSITION_POPULAR:
          return popular;
        case POSITION_RATED:
          return rated;
        case POSITION_FAVORITE:
          return favorites;
      }
      return null;
    }
  }


}
