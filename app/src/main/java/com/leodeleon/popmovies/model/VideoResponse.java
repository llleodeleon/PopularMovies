package com.leodeleon.popmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by leodeleon on 10/02/2017.
 */

public class VideoResponse {
  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("results")
  @Expose
  private ArrayList<Video> results = null;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ArrayList<Video> getVideos() {
    return results;
  }

  public void setResults(ArrayList<Video> videos) {
    this.results = videos;
  }
}
