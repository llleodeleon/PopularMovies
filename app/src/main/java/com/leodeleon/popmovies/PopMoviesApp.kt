package com.leodeleon.popmovies

import android.app.Activity
import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.leodeleon.popmovies.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by leodeleon on 08/02/2017.
 */

class PopMoviesApplication : Application(), HasActivityInjector {

  @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()
    AppInjector.init(this)

    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this)
      Timber.plant(Timber.DebugTree())
    } else {
      Timber.plant(CrashReportingTree())
    }
  }

  override fun activityInjector(): AndroidInjector<Activity> {
    return dispatchingAndroidInjector
  }

  private class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return
      }

      Crashlytics.log(message)

      if (t != null) {
        if (priority == Log.ERROR) {
          Crashlytics.logException(t)
        } else if (priority == Log.WARN) {
          Crashlytics.logException(t)
        }
      }
    }
  }
}
