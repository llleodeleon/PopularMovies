package com.leodeleon.popmovies.api;

import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by leodeleon on 08/02/2017.
 */

public interface MovieEndpoints {
  @GET("movie/popular")
  Call<Result> getPopularMovies();

  @GET("movie/top_rated")
  Call<Result> getTopRatedMovies();

  @GET("movie/{movieId}")
  Call<MovieDetail> getMovie(@Path("movieId") int movieId);
}
