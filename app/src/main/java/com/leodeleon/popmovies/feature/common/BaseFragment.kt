package com.leodeleon.popmovies.feature.common

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

  protected var subscriptions = CompositeDisposable()

  override fun onDestroy() {
    super.onDestroy()
    subscriptions.dispose()
  }
}