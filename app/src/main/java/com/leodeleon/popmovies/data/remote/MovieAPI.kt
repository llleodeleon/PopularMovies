package com.leodeleon.popmovies.data.remote

import com.leodeleon.popmovies.model.MovieDetail
import com.leodeleon.popmovies.model.MovieResponse
import com.leodeleon.popmovies.model.VideoResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class MovieAPI(val movieService: MovieService) {

  fun getPopularMovies(page: Int): Single<MovieResponse> {
    return movieService.getPopularMovies(page)
  }

  fun getTopRatedMovies(page: Int): Single<MovieResponse> {
    return movieService.getTopRatedMovies(page)
  }

  fun getMovieDetail(id: Int): Single<MovieDetail> {
    return movieService.getMovie(id)
  }

  fun getVideos(id: Int): Single<VideoResponse> {
    return movieService.getVideos(id)
  }


  interface MovieService {
    @GET("movie/popular") fun getPopularMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/top_rated") fun getTopRatedMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movieId}") fun getMovie(@Path("movieId") movieId: Int): Single<MovieDetail>

    @GET("movie/{movieId}/videos") fun getVideos(@Path("movieId") movieId: Int): Single<VideoResponse>
  }
}
