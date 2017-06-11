package com.leodeleon.popmovies.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.leodeleon.popmovies.model.Movie

@Database(entities = arrayOf(Movie::class), version = 1)
abstract class PopMoviesDB : RoomDatabase() {
  abstract fun movieDao(): MovieDao
}
