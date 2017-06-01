package com.leodeleon.popmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.leodeleon.popmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDb extends RoomDatabase {
  public abstract MovieDao movieDao();
}
