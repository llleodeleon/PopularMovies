package com.leodeleon.popmovies;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import com.google.firebase.crash.FirebaseCrash;
import com.leodeleon.popmovies.di.AppInjector;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by leodeleon on 08/02/2017.
 */



public class PopMoviesApplication extends Application implements HasActivityInjector {

  @Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

  @Override
  public void onCreate() {
    super.onCreate();
    AppInjector.init(this);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      Timber.plant(new CrashReportingTree());
    }
  }

  @Override public AndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }

  private static class CrashReportingTree extends Timber.Tree {
    @Override protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }

      FirebaseCrash.log(message);

      if (t != null) {
        if (priority == Log.ERROR) {
          FirebaseCrash.report(t);
        } else if (priority == Log.WARN) {
          FirebaseCrash.report(t);
        }
      }
    }
  }
}
