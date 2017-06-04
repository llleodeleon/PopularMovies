package com.leodeleon.popmovies.feature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.leodeleon.popmovies.R;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
  @Inject DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

  private FragmentManager manager = getSupportFragmentManager();

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



  public void addFragment(Fragment fragment) {
    manager.beginTransaction().add(R.id.container, fragment).addToBackStack(fragment.toString()).commit();
  }

}
