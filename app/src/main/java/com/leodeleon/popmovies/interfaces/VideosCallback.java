package com.leodeleon.popmovies.interfaces;

import com.leodeleon.popmovies.model.Video;

import java.util.ArrayList;

/**
 * Created by leodeleon on 10/02/2017.
 */

public interface VideosCallback {
  void callback(ArrayList<Video> videos);
}
