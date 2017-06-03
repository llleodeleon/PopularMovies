package com.leodeleon.popmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by leodeleon on 10/02/2017.
 */

@Entity
public class Movie {

  @SerializedName("id")
  @Expose
  @PrimaryKey
  private Integer id;
  @SerializedName("poster_path")
  @Expose
  private String posterPath;
  @SerializedName("adult")
  @Expose
  private Boolean adult;
  @SerializedName("overview")
  @Expose
  private String overview;
  @SerializedName("release_date")
  @Expose
  private String releaseDate;
  @SerializedName("genre_ids")
  @Expose
  @Ignore
  private List<Integer> genreIds = null;
  @SerializedName("original_title")
  @Expose
  private String originalTitle;
  @SerializedName("original_language")
  @Expose
  private String originalLanguage;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("backdrop_path")
  @Expose
  private String backdropPath;
  @SerializedName("popularity")
  @Expose
  private Double popularity;
  @SerializedName("vote_count")
  @Expose
  private Integer voteCount;
  @SerializedName("video")
  @Expose
  private Boolean video;
  @SerializedName("vote_average")
  @Expose
  private Double voteAverage;

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public Boolean getAdult() {
    return adult;
  }

  public void setAdult(Boolean adult) {
    this.adult = adult;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    Date date;
    Calendar calendar = Calendar.getInstance();
    try {
      date = format.parse(releaseDate);
      calendar.setTime(date);
    } catch (ParseException e) {
      FirebaseCrash.report(e);
    }
    return String.valueOf( calendar.get(Calendar.YEAR));
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public List<Integer> getGenreIds() {
    return genreIds;
  }

  public void setGenreIds(List<Integer> genreIds) {
    this.genreIds = genreIds;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public Integer getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(Integer voteCount) {
    this.voteCount = voteCount;
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

}
