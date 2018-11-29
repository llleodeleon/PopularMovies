package com.leodeleon.popmovies.data

import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PageKeyedDataSource
import com.leodeleon.popmovies.data.remote.MovieAPI
import com.leodeleon.popmovies.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MoviesDataSource
(val api: MovieAPI)
	: PageKeyedDataSource<Int, Movie>() {

	override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
		api.getPopularMovies(1)
				.subscribeBy {
					callback.onResult(it.results, 1,2)
				}
	}

	override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
		api.getPopularMovies(params.key)
				.subscribeBy {
					callback.onResult(it.results, params.key + 1)
				}
	}

	override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

}