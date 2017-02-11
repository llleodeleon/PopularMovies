package com.leodeleon.popmovies;


import android.app.Application;

import com.leodeleon.popmovies.api.API;

/**
 * Created by leodeleon on 08/02/2017.
 */



public class PopMoviesApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    API.initialize(this);
  }
}
