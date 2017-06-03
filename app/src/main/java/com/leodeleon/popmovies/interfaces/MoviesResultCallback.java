package com.leodeleon.popmovies.interfaces;

import com.leodeleon.popmovies.model.MovieResponse;

/**
 * Created by leodeleon on 08/02/2017.
 */

public interface MoviesResultCallback {
  void callback(MovieResponse movieResponse);
}
