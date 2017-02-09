package com.leodeleon.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by leodeleon on 08/02/2017.
 */

public class Movie {

  @SerializedName("adult")
  @Expose
  private Boolean adult;
  @SerializedName("backdrop_path")
  @Expose
  private String backdropPath;
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
  @SerializedName("original_language")
  @Expose
  private String originalLanguage;
  @SerializedName("original_title")
  @Expose
  private String originalTitle;
  @SerializedName("overview")
  @Expose
  private String overview;
  @SerializedName("popularity")
  @Expose
  private Double popularity;
  @SerializedName("poster_path")
  @Expose
  private Object posterPath;
  @SerializedName("release_date")
  @Expose
  private String releaseDate;
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
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("video")
  @Expose
  private Boolean video;
  @SerializedName("vote_average")
  @Expose
  private Double voteAverage;
  @SerializedName("vote_count")
  @Expose
  private Integer voteCount;

  public Boolean getAdult() {
    return adult;
  }

  public void setAdult(Boolean adult) {
    this.adult = adult;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

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

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public Object getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(Object posterPath) {
    this.posterPath = posterPath;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getVideo() {
    return video;
  }

  public void setVideo(Boolean video) {
    this.video = video;
  }

  public Double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(Double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public Integer getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(Integer voteCount) {
    this.voteCount = voteCount;
  }

}
