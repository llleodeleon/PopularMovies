package com.leodeleon.popmovies.util;

/**
 * Created by leodeleon on 08/02/2017.
 */

public class Constants {
  public static final String BASE_URL = "https://api.themoviedb.org/3/";
  public static final String YOUTUBE_BASE_URL = "https://m.youtube.com/watch?v=%s";
  public static final String YOUTUBE_IMG_URL = "https://img.youtube.com/vi/%s/hqdefault.jpg";
  public static final String MOVIES_IMG_URL = "https://image.tmdb.org/t/p/%s/%s";
  public static final int HTTP_RESPONSE_OK = 200;
  public static final String SORT_BY_POPULARITY = "sortByPopularity";
  public static final String SORT_BY_TOP_RATED = "sortByTopRated";
  public static final int UNINITIALIZED = 1;
  public static final int INITIALIZING = 2;
  public static final int INITIALIZED = 3;
}
