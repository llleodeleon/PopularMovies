package com.leodeleon.popularmovies.interfaces;

import com.leodeleon.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by leodeleon on 08/02/2017.
 */

public interface MovieResultsCallback {
  void callback(List<Movie> movies);
}
