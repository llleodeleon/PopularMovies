package com.leodeleon.popmovies.data;

import com.leodeleon.popmovies.data.local.MovieDB;
import com.leodeleon.popmovies.data.local.PopMoviesDB;
import com.leodeleon.popmovies.data.remote.MovieAPI;
import javax.inject.Inject;
import retrofit2.Retrofit;

public class RepositoryFactory {

  private Retrofit retrofit;
  private PopMoviesDB popMoviesDB;

  @Inject
  public RepositoryFactory(Retrofit retrofit, PopMoviesDB popMoviesDB) {
    this.retrofit = retrofit;
    this.popMoviesDB = popMoviesDB;
  }

  public MovieAPI createMovieAPI() {
    return new MovieAPI(retrofit);
  }

  public MovieDB createMovieDB() {
    return new MovieDB(popMoviesDB);
  }

}
