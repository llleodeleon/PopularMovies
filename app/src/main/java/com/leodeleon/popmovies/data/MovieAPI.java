package com.leodeleon.popmovies.data;

import com.leodeleon.popmovies.model.MovieDetail;
import com.leodeleon.popmovies.model.MoviesResult;
import com.leodeleon.popmovies.model.VideosResult;
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

  public Single<MoviesResult> getPopularMovies(int page) {
    return movieService.getPopularMovies(page);
  }

  interface MovieService {
    @GET("movie/popular")
    Single<MoviesResult> getPopularMovies(@Query("page")int page);

    @GET("movie/top_rated")
    Single<MoviesResult> getTopRatedMovies(@Query("page")int page);

    @GET("movie/{movieId}")
    Single<MovieDetail> getMovie(@Path("movieId") int movieId);

    @GET("movie/{movieId}/videos")
    Single<VideosResult> getVideos(@Path("movieId") int movieId);
  }


}
