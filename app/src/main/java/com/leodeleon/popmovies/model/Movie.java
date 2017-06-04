package com.leodeleon.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
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

public class Movie implements Parcelable
{
  @SerializedName("poster_path")
  @Expose
  private String posterPath;
  @SerializedName("adult")
  @Expose
  private boolean adult;
  @SerializedName("overview")
  @Expose
  private String overview;
  @SerializedName("release_date")
  @Expose
  private String releaseDate;
  @SerializedName("genre_ids")
  @Expose
  private List<Integer> genreIds = null;
  @SerializedName("id")
  @Expose
  private int id;
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
  private double popularity;
  @SerializedName("vote_count")
  @Expose
  private int voteCount;
  @SerializedName("video")
  @Expose
  private boolean video;
  @SerializedName("vote_average")
  @Expose
  private double voteAverage;
  public final static Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {


    @SuppressWarnings({
        "unchecked"
    })
    public Movie createFromParcel(Parcel in) {
      Movie instance = new Movie();
      instance.posterPath = ((String) in.readValue((String.class.getClassLoader())));
      instance.adult = ((boolean) in.readValue((boolean.class.getClassLoader())));
      instance.overview = ((String) in.readValue((String.class.getClassLoader())));
      instance.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
      in.readList(instance.genreIds, (java.lang.Integer.class.getClassLoader()));
      instance.id = ((int) in.readValue((int.class.getClassLoader())));
      instance.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
      instance.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
      instance.title = ((String) in.readValue((String.class.getClassLoader())));
      instance.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
      instance.popularity = ((double) in.readValue((double.class.getClassLoader())));
      instance.voteCount = ((int) in.readValue((int.class.getClassLoader())));
      instance.video = ((boolean) in.readValue((boolean.class.getClassLoader())));
      instance.voteAverage = ((double) in.readValue((double.class.getClassLoader())));
      return instance;
    }

    public Movie[] newArray(int size) {
      return (new Movie[size]);
    }

  }
      ;

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
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

  public double getPopularity() {
    return popularity;
  }

  public void setPopularity(double popularity) {
    this.popularity = popularity;
  }

  public int getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(int voteCount) {
    this.voteCount = voteCount;
  }

  public boolean isVideo() {
    return video;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  public double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(posterPath);
    dest.writeValue(adult);
    dest.writeValue(overview);
    dest.writeValue(releaseDate);
    dest.writeList(genreIds);
    dest.writeValue(id);
    dest.writeValue(originalTitle);
    dest.writeValue(originalLanguage);
    dest.writeValue(title);
    dest.writeValue(backdropPath);
    dest.writeValue(popularity);
    dest.writeValue(voteCount);
    dest.writeValue(video);
    dest.writeValue(voteAverage);
  }

  public int describeContents() {
    return 0;
  }

}