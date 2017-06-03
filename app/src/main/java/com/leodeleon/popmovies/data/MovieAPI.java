package com.leodeleon.popmovies.data;

import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.model.MovieResponse;
import com.leodeleon.popmovies.model.VideoResponse;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MovieAPI {
  private MovieService movieService;

  public MovieAPI(Retrofit retrofit) {
    movieService = retrofit.create(MovieService.class);
  }

  public Single<MovieResponse> getPopularMovies(int page) {
    return movieService.getPopularMovies(page);
  }

  public Single<MovieResponse> getTopRatedMovies(int page) {
    return movieService.getTopRatedMovies(page);
  }

  public Single<MovieDetail> getMovieDetail(int id) {
    return movieService.getMovie(id);
  }

  public Single<VideoResponse> getVideos(int id) {
    return movieService.getVideos(id);
  }


  interface MovieService {
    @GET("movie/popular") Single<MovieResponse> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated") Single<MovieResponse> getTopRatedMovies(@Query("page") int page);

    @GET("movie/{movieId}") Single<MovieDetail> getMovie(@Path("movieId") int movieId);

    @GET("movie/{movieId}/videos") Single<VideoResponse> getVideos(@Path("movieId") int movieId);
  }
}
