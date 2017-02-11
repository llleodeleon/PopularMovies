package com.leodeleon.popmovies.api;

import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.model.MoviesResult;
import com.leodeleon.popmovies.model.VideosResult;

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
  Call<MoviesResult> getPopularMovies(@Query("page")int page);

  @GET("movie/top_rated")
  Call<MoviesResult> getTopRatedMovies(@Query("page")int page);

  @GET("movie/{movieId}")
  Call<MovieDetail> getMovie(@Path("movieId") int movieId);

  @GET("movie/{movieId}/videos")
  Call<VideosResult> getVideos(@Path("movieId") int movieId);
}
