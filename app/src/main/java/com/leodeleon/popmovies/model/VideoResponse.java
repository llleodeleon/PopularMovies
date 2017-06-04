package com.leodeleon.popmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by leodeleon on 10/02/2017.
 */

public class VideoResponse {
  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("results")
  @Expose
  private List<Video> results = null;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Video> getVideos() {
    return results;
  }

  public void setResults(List<Video> videos) {
    this.results = videos;
  }
}
