package com.leodeleon.popmovies.data.local;

import com.leodeleon.popmovies.model.Movie;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;

public class MovieDB {

  private MovieDao movieDao;

  public MovieDB(PopMoviesDB popMoviesDB) {
    movieDao = popMoviesDB.movieDao();
  }

  public Completable saveMovie(Movie movie) {
   return Completable.create(completableEmitter -> {
     movieDao.insert(movie);
     completableEmitter.onComplete();
   });
  }

  public Completable deleteMovie(Movie movie) {
    return Completable.create(completableEmitter -> {
      movieDao.delete(movie);
      completableEmitter.onComplete();
    });
  }

  public Single<List<Movie>> getFavMovies() {
    return movieDao.getFavoriteMovies().onBackpressureBuffer().firstOrError();
  }

}
