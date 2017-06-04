package com.leodeleon.popmovies.data;

import com.leodeleon.popmovies.data.local.MovieDB;
import com.leodeleon.popmovies.data.remote.MovieAPI;
import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.model.MovieResponse;
import com.leodeleon.popmovies.model.Video;
import com.leodeleon.popmovies.model.VideoResponse;
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

  @Inject public MovieRepository(RepositoryFactory factory) {
    movieAPI = factory.createMovieAPI();
    movieDB = factory.createMovieDB();
  }

  public Single<List<Movie>> getPopMovies(int page) {
    return getMovies(movieAPI.getPopularMovies(page));
  }

  public Single<List<Movie>> getTopMovies(int page) {
    return getMovies(movieAPI.getTopRatedMovies(page));
  }

  public Single<List<Movie>> getFavMovies() {
    return movieDB.getFavMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }

  public Completable saveMovie(Movie movie) {
    return movieDB.saveMovie(movie)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }

  public Completable deleteMovie(Movie movie) {
    return movieDB.deleteMovie(movie)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }

  public Single<MovieDetail> getMovieDetail(int id) {
    return movieAPI.getMovieDetail(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }

  public Single<List<String>> getVideoKeys(int id) {
    return movieAPI.getVideos(id)
        .toObservable()
        .flatMapIterable(VideoResponse::getVideos)
        .flatMapSingle(new Function<Video, SingleSource<String>>() {
          @Override public SingleSource<String> apply(@NonNull Video video) throws Exception {
            return Single.just(video.getKey());
          }
        })
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
  }

  private Single<List<Movie>> getMovies(Single<MovieResponse> movieResponseSingle) {
    return movieResponseSingle.flatMap(new Function<MovieResponse, SingleSource<List<Movie>>>() {
      @Override public SingleSource<List<Movie>> apply(@NonNull MovieResponse movieResponse) throws Exception {
        return Single.just(movieResponse.getMovies());
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
  }
}
