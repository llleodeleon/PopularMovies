package com.leodeleon.popmovies.api;

import com.google.firebase.crash.FirebaseCrash;
import com.leodeleon.popmovies.interfaces.MovieCallback;
import com.leodeleon.popmovies.interfaces.MovieDetailCallback;
import com.leodeleon.popmovies.interfaces.MoviesResultCallback;
import com.leodeleon.popmovies.interfaces.StringsCallback;
import com.leodeleon.popmovies.interfaces.VideosCallback;
import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.model.MoviesResult;
import com.leodeleon.popmovies.model.Video;
import com.leodeleon.popmovies.model.VideosResult;
import com.leodeleon.popmovies.util.Constants;

import java.util.ArrayList;
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

  public void getMovieDetail(int movieId, final MovieDetailCallback movieCallback) {

    Call<MovieDetail> call = movieEndpoints.getMovie(movieId);

    call.enqueue(new Callback<MovieDetail>() {
      @Override
      public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK)
          movieCallback.callback(response.body());
      }

      @Override
      public void onFailure(Call<MovieDetail> call, Throwable t) {
        FirebaseCrash.report(t);
      }
    });

  }

  public void getVideos(int movieId, final StringsCallback videosCallback) {
    Call<VideosResult> call = movieEndpoints.getVideos(movieId);

    call.enqueue(new Callback<VideosResult>() {
      @Override
      public void onResponse(Call<VideosResult> call, Response<VideosResult> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK) {
          ArrayList<String> videoIds = new ArrayList<>();
          for (Video video : response.body().getVideos()) {
            videoIds.add(video.getKey());
          }
          videosCallback.callback(videoIds);
        }

      }

      @Override
      public void onFailure(Call<VideosResult> call, Throwable t) {
        FirebaseCrash.report(t);
      }
    });
  }

  public void getPopularMovies(int page, final MoviesResultCallback moviesResultCallback) {

    Call<MoviesResult> call = movieEndpoints.getPopularMovies(page);

    call.enqueue(new Callback<MoviesResult>() {
      @Override
      public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK)
          moviesResultCallback.callback(response.body());
      }

      @Override
      public void onFailure(Call<MoviesResult> call, Throwable t) {
        FirebaseCrash.report(t);
      }
    });
  }

  public void getTopRatedMovies(int page, final MoviesResultCallback moviesResultCallback) {

    Call<MoviesResult> call = movieEndpoints.getTopRatedMovies(page);

    call.enqueue(new Callback<MoviesResult>() {
      @Override
      public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
        if (response.code() == Constants.HTTP_RESPONSE_OK)
          moviesResultCallback.callback(response.body());
      }

      @Override
      public void onFailure(Call<MoviesResult> call, Throwable t) {
        FirebaseCrash.report(t);
      }
    });
  }
}
