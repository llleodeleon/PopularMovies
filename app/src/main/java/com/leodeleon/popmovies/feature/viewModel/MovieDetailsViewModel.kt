package com.leodeleon.popmovies.feature.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leodeleon.popmovies.data.MovieRepository
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.model.MovieDetail
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsViewModel @Inject
constructor(
    private val movieRepository: MovieRepository,
    private val movieSubject: PublishSubject<Movie>
) : ViewModel() {

  val detailLiveData = MutableLiveData<MovieDetail>()
  val videoLiveData = MutableLiveData<List<String>>()
  private val disposable = CompositeDisposable()

  override fun onCleared() {
    super.onCleared()
    disposable.clear()
  }

  fun loadDetails(id: Int) {
    val d1 = movieRepository.getMovieDetail(id).subscribe(Consumer<MovieDetail> { detailLiveData.postValue(it) })
    val d2 = movieRepository.getVideoKeys(id).subscribe(Consumer<List<String>> { videoLiveData.postValue(it) })
    disposable.add(d1)
    disposable.add(d2)
  }

  fun addFavorite(movie: Movie) {
    movie.favorite = 1
    movieRepository.saveMovie(movie).subscribe(MovieCompletableObserver(movie))
  }

  fun removeFavorite(movie: Movie) {
    movie.favorite = 0
    movieRepository.saveMovie(movie).subscribe(MovieCompletableObserver(movie))
  }

  private inner class MovieCompletableObserver
  internal constructor(private val movie: Movie) : CompletableObserver {

    override fun onSubscribe(d: Disposable) {
      disposable.add(d)
    }

    override fun onComplete() {
      movieSubject.onNext(movie)
    }

    override fun onError(e: Throwable) {
      Timber.e(e)
    }
  }
}
