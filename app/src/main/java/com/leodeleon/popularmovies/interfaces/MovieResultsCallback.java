package com.leodeleon.popularmovies.interfaces;

import com.leodeleon.popularmovies.model.Movie;
import com.leodeleon.popularmovies.model.MovieResults;

import java.util.List;

/**
 * Created by leodeleon on 08/02/2017.
 */

public interface MovieResultsCallback {
  void callback(MovieResults movies);
}
