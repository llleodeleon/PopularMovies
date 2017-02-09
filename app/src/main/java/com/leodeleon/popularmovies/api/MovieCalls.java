package com.leodeleon.popularmovies.api;

import com.leodeleon.popularmovies.R;
import com.leodeleon.popularmovies.interfaces.MovieCallback;
import com.leodeleon.popularmovies.interfaces.MoviesCallback;
import com.leodeleon.popularmovies.model.Movie;
import com.leodeleon.popularmovies.util.Constants;

import java.util.Arrays;
import java.util.List;

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

    Call<Movie> call = movieEndpoints.getMovie(movieId);

    call.enqueue(new Callback<Movie>() {
      @Override
      public void onResponse(Call<Movie> call, Response<Movie> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK)
          movieCallback.callback(response.body());
      }

      @Override
      public void onFailure(Call<Movie> call, Throwable t) {
        System.out.println(Arrays.toString(t.getStackTrace()));
      }
    });

  }


  public void getPopularMovies(final MoviesCallback moviesCallback) {

    Call<List<Movie>> call = movieEndpoints.getPopularMovies();

    call.enqueue(new Callback<List<Movie>>() {
      @Override
      public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK)
          moviesCallback.callback(response.body());
      }

      @Override
      public void onFailure(Call<List<Movie>> call, Throwable t) {
        System.out.println(Arrays.toString(t.getStackTrace()));
      }
    });
  }

  public void getTopRatedMovies(final MoviesCallback moviesCallback) {

    Call<List<Movie>> call = movieEndpoints.getTopRatedMovies();

    call.enqueue(new Callback<List<Movie>>() {
      @Override
      public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK)
          moviesCallback.callback(response.body());
      }

      @Override
      public void onFailure(Call<List<Movie>> call, Throwable t) {
        System.out.println(Arrays.toString(t.getStackTrace()));
      }
    });
  }
}
