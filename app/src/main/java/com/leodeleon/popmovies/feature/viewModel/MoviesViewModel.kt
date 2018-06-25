package com.leodeleon.popmovies.feature.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leodeleon.popmovies.data.MovieRepository
import com.leodeleon.popmovies.model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class MoviesViewModel
constructor(
    private val movieRepository: MovieRepository,
    private val movieSubject: PublishSubject<Movie>
) : ViewModel() {
  val popMoviesLiveData = MutableLiveData<List<Movie>>()
  val topMoviesLiveData = MutableLiveData<List<Movie>>()
  val favMoviesLiveData = MutableLiveData<List<Movie>>()
  private val disposable = CompositeDisposable()

  init {
    subscribe()
  }

  fun loadPopularMovies(page: Int) {
    val d1 = movieRepository.getPopMovies(page).subscribe(Consumer<List<Movie>> { popMoviesLiveData.postValue(it) })
    disposable.add(d1)
  }

  fun loadTopRatedMovies(page: Int) {
    val d2 = movieRepository.getTopMovies(page).subscribe(Consumer<List<Movie>> { topMoviesLiveData.postValue(it) })
    disposable.add(d2)
  }

  fun loadFavoriteMovies() {
    val d3 = movieRepository.getFavMovies().subscribe(Consumer<List<Movie>> { favMoviesLiveData.postValue(it) })
    disposable.add(d3)
  }

  private fun subscribe() {
    val d4 = movieSubject.subscribe({ _ -> loadFavoriteMovies() },{ Timber.e(it) })
    disposable.add(d4)
  }

  override fun onCleared() {
    super.onCleared()
    disposable.clear()
  }

}
