package com.leodeleon.popmovies;

import android.app.Activity;
import android.app.Application;
import com.leodeleon.popmovies.api.API;
import com.leodeleon.popmovies.di.AppInjector;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;

/**
 * Created by leodeleon on 08/02/2017.
 */



public class PopMoviesApplication extends Application implements HasActivityInjector {

  @Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

  @Override
  public void onCreate() {
    super.onCreate();
    API.initialize(this);
    AppInjector.init(this);
  }

  @Override public AndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }
}
