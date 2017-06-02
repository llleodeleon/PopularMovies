package com.leodeleon.popmovies.data;

import com.leodeleon.popmovies.model.MoviesResult;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository {

  private MovieAPI movieAPI;
  private MovieDB movieDB;

  public MovieRepository(RepositoryFactory factory) {
    movieAPI = factory.createMovieAPI();
    movieDB = factory.createMovieDB();
  }


  public Single<MoviesResult> getPopularMovies(int page) {
    return movieAPI.getPopularMovies(page)
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
