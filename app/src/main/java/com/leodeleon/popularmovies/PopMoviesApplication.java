package com.leodeleon.popularmovies;


import android.app.Application;
import android.content.Context;

import com.leodeleon.popularmovies.api.API;

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
