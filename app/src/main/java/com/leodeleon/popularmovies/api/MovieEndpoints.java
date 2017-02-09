package com.leodeleon.popularmovies.api;

import com.leodeleon.popularmovies.model.Movie;
import com.leodeleon.popularmovies.model.MovieResults;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by leodeleon on 08/02/2017.
 */

public interface MovieEndpoints {
  @GET("movie/popular")
  Call<MovieResults> getPopularMovies();

  @GET("movie/top_rated")
  Call<MovieResults> getTopRatedMovies();

  @GET("movie/{movieId}")
  Call<Movie> getMovie(@Path("movieId") int movieId);
}
