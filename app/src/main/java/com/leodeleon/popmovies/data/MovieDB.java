package com.leodeleon.popmovies.data;

import com.leodeleon.popmovies.model.MovieDetail;
import io.reactivex.Completable;

public class MovieDB {

  private MovieDao movieDao;

  public MovieDB(PopMoviesDB popMoviesDB) {
    movieDao = popMoviesDB.movieDao();
  }

  public Completable saveMovie(MovieDetail movieDetail) {
   return Completable.create(completableEmitter -> {
     movieDao.insert(movieDetail);
     completableEmitter.onComplete();
   });
  }

}
