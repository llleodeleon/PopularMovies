package com.leodeleon.popmovies.feature.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leodeleon.popmovies.data.MovieRepository
import com.leodeleon.popmovies.model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class FavMoviesViewModel
constructor(
    private val movieRepository: MovieRepository,
    private val movieSubject: PublishSubject<Movie>
) : BaseViewModel() {
  val favMoviesLiveData = MutableLiveData<List<Movie>>()

  init {
    subscribe()
  }

  fun loadFavoriteMovies() {
    movieRepository.getFavMovies()
        .subscribeBy {
          favMoviesLiveData.value = it }
        .addTo(subscriptions)
  }

  private fun subscribe() {
    movieSubject.subscribeBy {
      loadFavoriteMovies() }
    .addTo(subscriptions)
  }
}
