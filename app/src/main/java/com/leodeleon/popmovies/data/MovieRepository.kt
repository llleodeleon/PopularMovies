package com.leodeleon.popmovies.data

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.leodeleon.popmovies.MoviesDataSourceFactory
import com.leodeleon.popmovies.data.local.MovieDB
import com.leodeleon.popmovies.data.remote.MovieAPI
import com.leodeleon.popmovies.feature.adapters.MoviesBoundaryCallback
import com.leodeleon.popmovies.model.Movie
import com.leodeleon.popmovies.model.MovieDetail
import com.leodeleon.popmovies.model.MovieResponse
import com.leodeleon.popmovies.model.Video
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.toSingle
import io.reactivex.schedulers.Schedulers

class MovieRepository
constructor(val movieAPI: MovieAPI, val movieDB: MovieDB,val  factory: MoviesDataSourceFactory) {

  fun getPopMovies(subscriptions: CompositeDisposable): Observable<PagedList<Movie>> {
    val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(20)
        .build()

    return RxPagedListBuilder(factory, config)
        .setInitialLoadKey(1)
        .setFetchScheduler(Schedulers.io())
        .setNotifyScheduler(AndroidSchedulers.mainThread())
         .buildObservable()
  }

  fun getTopMovies(page: Int): Single<List<Movie>> {
    return getMovies(movieAPI.getTopRatedMovies(page))
  }

  fun getFavMovies() : Single<List<Movie>>{
    return movieDB.getFavoriteMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
  }

  fun saveMovie(movie: Movie): Completable {
    return movieDB.saveMovie(movie)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
  }

  fun deleteMovie(movie: Movie): Completable {
    return movieDB.deleteMovie(movie)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
  }

  fun getMovieDetail(id: Int): Single<MovieDetail> {
    return movieAPI.getMovieDetail(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
  }

  fun getVideoKeys(id: Int): Single<List<String>> {
    return movieAPI.getVideos(id)
        .toObservable()
        .flatMapIterable<Video> { it.results }
        .flatMapSingle { video -> Single.just<String>(video.key) }
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
  }

  private fun getMovies(movieResponseSingle: Single<MovieResponse>): Single<List<Movie>> {
    return movieResponseSingle.flatMap { movieResponse -> Single.just<List<Movie>>(movieResponse.results) }
        .subscribeOn(
        Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io())
  }
}
