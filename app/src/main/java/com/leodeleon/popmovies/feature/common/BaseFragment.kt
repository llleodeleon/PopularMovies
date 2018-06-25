package com.leodeleon.popmovies.feature.common

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

  protected var disposable = CompositeDisposable()

  override fun onPause() {
    super.onPause()
    disposable.clear()
  }
}