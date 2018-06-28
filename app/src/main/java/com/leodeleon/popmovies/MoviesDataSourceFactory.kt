package com.leodeleon.popmovies

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.leodeleon.popmovies.data.MoviesDataSource
import com.leodeleon.popmovies.data.remote.MovieAPI
import com.leodeleon.popmovies.model.Movie

class MoviesDataSourceFactory(
		val api: MovieAPI
): DataSource.Factory<Int, Movie>() {
	private val mutableLiveData = MutableLiveData<MoviesDataSource>()

	override fun create(): DataSource<Int, Movie> {
		val dataSource = MoviesDataSource(api)
		mutableLiveData.postValue(dataSource)
		return dataSource
	}
}