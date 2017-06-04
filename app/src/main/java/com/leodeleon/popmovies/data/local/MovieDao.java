package com.leodeleon.popmovies.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.leodeleon.popmovies.model.Movie;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface MovieDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Movie movieDetail);

  @Query("SELECT * FROM movies WHERE isFavorite = 1")
  Flowable<List<Movie>> getFavoriteMovies();

  @Delete
  void delete(Movie movieDetailDetail);

}
