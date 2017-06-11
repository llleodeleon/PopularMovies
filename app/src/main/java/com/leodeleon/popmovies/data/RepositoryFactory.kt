package com.leodeleon.popmovies.data

import com.leodeleon.popmovies.data.local.MovieDB
import com.leodeleon.popmovies.data.local.PopMoviesDB
import com.leodeleon.popmovies.data.remote.MovieAPI
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryFactory @Inject
constructor(private val retrofit: Retrofit, private val popMoviesDB: PopMoviesDB) {

  fun createMovieAPI(): MovieAPI {
    return MovieAPI(retrofit)
  }

  fun createMovieDB(): MovieDB {
    return MovieDB(popMoviesDB)
  }

}
