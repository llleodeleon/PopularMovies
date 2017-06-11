package com.leodeleon.popmovies.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.leodeleon.popmovies.PopMoviesApplication
import com.leodeleon.popmovies.di.component.DaggerAppComponent
import com.leodeleon.popmovies.di.modules.AppModule
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {

  fun init(application: PopMoviesApplication) {
    DaggerAppComponent.builder()
        .application(application)
        .appModule(AppModule(application))
        .build()
        .inject(application)

    application
        .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
          override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            handleActivity(activity)
          }

          override fun onActivityStarted(activity: Activity) {}

          override fun onActivityResumed(activity: Activity) {}

          override fun onActivityPaused(activity: Activity) {}

          override fun onActivityStopped(activity: Activity) {}

          override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

          override fun onActivityDestroyed(activity: Activity) {}
        })
  }

  private fun handleActivity(activity: Activity) {
    if (activity is HasSupportFragmentInjector) {
      AndroidInjection.inject(activity)
    }
    if (activity is FragmentActivity) {
      activity.supportFragmentManager
          .registerFragmentLifecycleCallbacks(
              object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?,
                    savedInstanceState: Bundle?) {
                  if (f is Injectable) {
                    AndroidSupportInjection.inject(f)
                  }
                }
              }, true)
    }
  }
}