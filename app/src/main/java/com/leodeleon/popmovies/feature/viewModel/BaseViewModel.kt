package com.leodeleon.popmovies.feature.viewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

open class BaseViewModel: ViewModel(){

	protected val subscriptions = CompositeDisposable()

	protected val logError : (Throwable) -> Unit = {
		Timber.e(it)
	}

	override fun onCleared() {
		super.onCleared()
		subscriptions.clear()
	}

}