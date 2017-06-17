package com.leodeleon.popmovies.feature.common

import android.arch.lifecycle.LifecycleFragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : LifecycleFragment() {

  protected var disposable = CompositeDisposable()

  override fun onPause() {
    super.onPause()
    disposable.clear()
  }
}