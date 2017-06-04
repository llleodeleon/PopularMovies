package com.leodeleon.popmovies.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import com.leodeleon.popmovies.model.MovieDetail;

@Dao
public interface MovieDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(MovieDetail movieDetail);

  @Delete
  void delete(MovieDetail movieDetailDetail);

}
