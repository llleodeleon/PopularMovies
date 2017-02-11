package com.leodeleon.popmovies.interfaces;

import com.leodeleon.popmovies.model.Movie;
import com.leodeleon.popmovies.model.MovieDetail;

/**
 * Created by leodeleon on 08/02/2017.
 */

public interface MovieCallback {
  void callback(Movie movie);
}
