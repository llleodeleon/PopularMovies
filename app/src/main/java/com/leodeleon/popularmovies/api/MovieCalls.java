package com.leodeleon.popularmovies.api;

import com.leodeleon.popularmovies.interfaces.MovieCallback;
import com.leodeleon.popularmovies.interfaces.MoviesResultCallback;
import com.leodeleon.popularmovies.model.MovieDetail;
import com.leodeleon.popularmovies.model.Result;
import com.leodeleon.popularmovies.util.Constants;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leodeleon on 08/02/2017.
 */

public class MovieCalls {

  private static MovieCalls instance;
  private MovieEndpoints movieEndpoints;

  public static MovieCalls getInstance() {
    if (instance == null) {
      instance = new MovieCalls();
      instance.movieEndpoints = API.getInstance().getRetrofit().create(MovieEndpoints.class);
    }
    return instance;
  }

  public void getMovie(int movieId, final MovieCallback movieCallback) {

    Call<MovieDetail> call = movieEndpoints.getMovie(movieId);

    call.enqueue(new Callback<MovieDetail>() {
      @Override
      public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK)
          movieCallback.callback(response.body());
      }

      @Override
      public void onFailure(Call<MovieDetail> call, Throwable t) {
        System.out.println(Arrays.toString(t.getStackTrace()));
      }
    });

  }


  public void getPopularMovies(final MoviesResultCallback moviesResultCallback) {

    Call<Result> call = movieEndpoints.getPopularMovies();

    call.enqueue(new Callback<Result>() {
      @Override
      public void onResponse(Call<Result> call, Response<Result> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK)
          moviesResultCallback.callback(response.body());
      }

      @Override
      public void onFailure(Call<Result> call, Throwable t) {
        System.out.println(Arrays.toString(t.getStackTrace()));
      }
    });
  }

  public void getTopRatedMovies(final MoviesResultCallback moviesResultCallback) {

    Call<Result> call = movieEndpoints.getTopRatedMovies();

    call.enqueue(new Callback<Result>() {
      @Override
      public void onResponse(Call<Result> call, Response<Result> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK)
          moviesResultCallback.callback(response.body());
      }

      @Override
      public void onFailure(Call<Result> call, Throwable t) {
        System.out.println(Arrays.toString(t.getStackTrace()));
      }
    });
  }
}
