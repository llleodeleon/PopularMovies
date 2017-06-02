package com.leodeleon.popmovies.data;

public class MovieDB {

  private MovieDao movieDao;

  public MovieDB(PopMoviesDB popMoviesDB) {
    movieDao = popMoviesDB.movieDao();
  }

}
