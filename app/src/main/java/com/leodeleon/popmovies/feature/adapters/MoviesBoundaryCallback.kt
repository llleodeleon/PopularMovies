package com.leodeleon.popmovies.feature.adapters

import android.arch.paging.PagedList
import com.leodeleon.popmovies.data.remote.MovieAPI
import com.leodeleon.popmovies.model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MoviesBoundaryCallback(val api: MovieAPI,val subscriptions: CompositeDisposable):
		PagedList.BoundaryCallback<Movie>() {
	private var page = 1
	override fun onZeroItemsLoaded() {
		fetch()
	}

	override fun onItemAtEndLoaded(itemAtEnd: Movie) {
		fetch()
	}

	private fun fetch() {
		api.getPopularMovies(page)
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io())
				.subscribeBy {
					page ++
				}.addTo(subscriptions)
	}
}