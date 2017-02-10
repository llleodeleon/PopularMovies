package com.leodeleon.popmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by leodeleon on 08/02/2017.
 */

public class MovieDetail extends Movie {

  @SerializedName("belongs_to_collection")
  @Expose
  private Object belongsToCollection;
  @SerializedName("budget")
  @Expose
  private Integer budget;
  @SerializedName("homepage")
  @Expose
  private String homepage;
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("imdb_id")
  @Expose
  private String imdbId;
  @SerializedName("popularity")
  @Expose
  private Double popularity;
  @SerializedName("revenue")
  @Expose
  private Integer revenue;
  @SerializedName("runtime")
  @Expose
  private Integer runtime;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("tagline")
  @Expose
  private String tagline;

public Object getBelongsToCollection() {
    return belongsToCollection;
  }

  public void setBelongsToCollection(Object belongsToCollection) {
    this.belongsToCollection = belongsToCollection;
  }

  public Integer getBudget() {
    return budget;
  }

  public void setBudget(Integer budget) {
    this.budget = budget;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getImdbId() {
    return imdbId;
  }

  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public Integer getRevenue() {
    return revenue;
  }

  public void setRevenue(Integer revenue) {
    this.revenue = revenue;
  }

  public Integer getRuntime() {
    return runtime;
  }

  public void setRuntime(Integer runtime) {
    this.runtime = runtime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTagline() {
    return tagline;
  }

  public void setTagline(String tagline) {
    this.tagline = tagline;
  }

}
