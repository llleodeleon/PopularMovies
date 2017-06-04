package com.leodeleon.popmovies.feature;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import com.leodeleon.popmovies.R;
import com.leodeleon.popmovies.feature.view.MoviePagerFragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
  @Inject DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

  private FragmentManager manager = getSupportFragmentManager();

  MoviePagerFragment mainFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onBackPressed() {
    if (manager.getBackStackEntryCount() == 0) {
      super.onBackPressed();
    }else {
      manager.popBackStackImmediate();
    }
  }

  @Override public AndroidInjector<Fragment> supportFragmentInjector() {
    return dispatchingAndroidInjector;
  }


  public void addFragment(Fragment fragment, View sharedElement, String transitionName) {
    mainFragment = (MoviePagerFragment) manager.findFragmentById(R.id.fragment_pager);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      mainFragment.setSharedElementEnterTransition(new AutoTransition());
      mainFragment.setSharedElementReturnTransition(new AutoTransition());
      fragment.setEnterTransition(new Fade());
      fragment.setExitTransition(new Fade());
      fragment.setSharedElementEnterTransition(new AutoTransition());
      fragment.setSharedElementReturnTransition(new AutoTransition());
    }

    manager.beginTransaction()
        .addSharedElement(sharedElement, transitionName)
        .add(R.id.container, fragment)
        .addToBackStack(fragment.toString())
        .commit();
  }

  public void addFragment(Fragment fragment) {
    manager.beginTransaction()
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out)
        .add(R.id.container, fragment)
        .addToBackStack(fragment.toString())
        .commit();
  }
}
