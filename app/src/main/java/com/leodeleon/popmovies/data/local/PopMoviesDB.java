package com.leodeleon.popmovies.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.leodeleon.popmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class PopMoviesDB extends RoomDatabase {
  public abstract MovieDao movieDao();
}
