package com.leodeleon.popmovies.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.leodeleon.popmovies.model.Movie
import io.reactivex.Flowable

@Dao
interface MovieDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(movieDetail: Movie)

  @Query("SELECT * FROM movies WHERE favorite = 1")
  fun getFavoriteMovies() : Flowable<List<Movie>>

  @Delete
  fun delete(movieDetailDetail: Movie)

}
