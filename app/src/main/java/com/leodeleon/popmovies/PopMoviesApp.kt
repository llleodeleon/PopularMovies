package com.leodeleon.popmovies

import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho
import com.google.firebase.crash.FirebaseCrash
import com.leodeleon.popmovies.di.appModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.startKoin
import timber.log.Timber

/**
 * Created by leodeleon on 08/02/2017.
 */

class PopMoviesApp : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin(listOf(appModule))
    RxJavaPlugins.setErrorHandler {
      Timber.e(it)
    }

    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this)
      Timber.plant(Timber.DebugTree())
    } else {
      Timber.plant(CrashReportingTree())
    }
  }


  private class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return
      }

      FirebaseCrash.log(message)

      if (t != null) {
        if (priority == Log.ERROR) {
          FirebaseCrash.report(t)
        } else if (priority == Log.WARN) {
          FirebaseCrash.report(t)
        }
      }
    }
  }
}
