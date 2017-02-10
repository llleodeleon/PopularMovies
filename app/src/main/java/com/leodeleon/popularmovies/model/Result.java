package com.leodeleon.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leodeleon on 09/02/2017.
 */

public class Result {
  @SerializedName("page")
  @Expose
  private Integer page;
  @SerializedName("results")
  @Expose
  private List<Movie> results = new ArrayList<>();
  @SerializedName("total_results")
  @Expose
  private Integer totalResults;
  @SerializedName("total_pages")
  @Expose
  private Integer totalPages;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public ArrayList<Movie> getMovies() {
    return new ArrayList<>(results);
  }

  public void setMovies(ArrayList<Movie> movies) {
    this.results = movies;
  }

  public Integer getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(Integer totalResults) {
    this.totalResults = totalResults;
  }


  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }
}
