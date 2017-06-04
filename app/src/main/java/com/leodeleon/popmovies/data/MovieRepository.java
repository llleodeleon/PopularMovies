package com.leodeleon.popmovies.data;

import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.model.MovieResponse;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

public class MovieRepository {
  private static final String TAG = "MovieRepository";
  private MovieAPI movieAPI;
  private MovieDB movieDB;

  @Inject
  public MovieRepository(RepositoryFactory factory) {
    movieAPI = factory.createMovieAPI();
    movieDB = factory.createMovieDB();
  }

  public Single<List<Movie>> getPopMovies(int page) {
    return movieAPI.getPopularMovies(page)
        .subscribeOn(Schedulers.io())
        .flatMap(new Function<MovieResponse, SingleSource<List<Movie>>>() {
          @Override public SingleSource<List<Movie>> apply(@NonNull MovieResponse movieResponse) throws Exception {
            return Single.just(movieResponse.getMovies());
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }

  public Single<MovieResponse> getPopularMovies(int page) {
    return movieAPI.getPopularMovies(page)
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Single<MovieResponse> getTopRatedMovies(int page) {
    return movieAPI.getTopRatedMovies(page)
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Completable saveMovie(MovieDetail movieDetail) {
    return movieDB.saveMovie(movieDetail)
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

}
