package com.leodeleon.popmovies.feature;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.custom.LockableViewPager;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

import static com.leodeleon.popmovies.feature.MoviesFragment.POSITION_POPULAR;
import static com.leodeleon.popmovies.feature.MoviesFragment.POSITION_RATED;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
  @Inject DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

  private static final String TAG = "MainActivity";
  @BindView(R.id.viewpager)
  LockableViewPager mViewPager;
  @BindView(R.id.toolbar)
  public Toolbar mToolbar;
  @BindView(R.id.tab_layout)
  TabLayout mTabLayout;

  @BindString(R.string.sort_popular)
  String popular;
  @BindString(R.string.sort_rated)
  String rated;

  private PagerAdapter adapter;

  private Unbinder unbinder;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    adapter = new PagerAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(adapter);
    mTabLayout.setupWithViewPager(mViewPager);
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
      return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
      switch (position) {
        case POSITION_POPULAR:
          return popular;
        case POSITION_RATED:
          return rated;
      }
      return null;
    }
  }


}
