package com.leodeleon.popmovies.model;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by leodeleon on 08/02/2017.
 */

public class MovieDetail implements Parcelable {
  @SerializedName("id")
  @Expose
  @PrimaryKey
  private int id;
  @SerializedName("adult")
  @Expose
  private boolean adult;
  @SerializedName("backdrop_path")
  @Expose
  private String backdropPath;
  @SerializedName("belongs_to_collection")
  @Expose
  @Ignore
  private BelongsToCollection belongsToCollection;
  @SerializedName("budget")
  @Expose
  private int budget;
  @SerializedName("genres")
  @Expose
  @Ignore
  private List<Genre> genres = null;
  @SerializedName("homepage")
  @Expose
  private String homepage;
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
  private double popularity;
  @SerializedName("poster_path")
  @Expose
  private String posterPath;
  @SerializedName("production_companies")
  @Expose
  @Ignore
  private List<ProductionCompany> productionCompanies = null;
  @SerializedName("production_countries")
  @Expose
  @Ignore
  private List<ProductionCountry> productionCountries = null;
  @SerializedName("release_date")
  @Expose
  private String releaseDate;
  @SerializedName("revenue")
  @Expose
  private int revenue;
  @SerializedName("runtime")
  @Expose
  private int runtime;
  @SerializedName("spoken_languages")
  @Expose
  @Ignore
  private List<SpokenLanguage> spokenLanguages = null;
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
  private boolean video;
  @SerializedName("vote_average")
  @Expose
  private double voteAverage;
  @SerializedName("vote_count")
  @Expose
  private int voteCount;

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public BelongsToCollection getBelongsToCollection() {
    return belongsToCollection;
  }

  public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
    this.belongsToCollection = belongsToCollection;
  }

  public int getBudget() {
    return budget;
  }

  public void setBudget(int budget) {
    this.budget = budget;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
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

  public double getPopularity() {
    return popularity;
  }

  public void setPopularity(double popularity) {
    this.popularity = popularity;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public List<ProductionCompany> getProductionCompanies() {
    return productionCompanies;
  }

  public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
    this.productionCompanies = productionCompanies;
  }

  public List<ProductionCountry> getProductionCountries() {
    return productionCountries;
  }

  public void setProductionCountries(List<ProductionCountry> productionCountries) {
    this.productionCountries = productionCountries;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public int getRevenue() {
    return revenue;
  }

  public void setRevenue(int revenue) {
    this.revenue = revenue;
  }

  public int getRuntime() {
    return runtime;
  }

  public void setRuntime(int runtime) {
    this.runtime = runtime;
  }

  public List<SpokenLanguage> getSpokenLanguages() {
    return spokenLanguages;
  }

  public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
    this.spokenLanguages = spokenLanguages;
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

  public int getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(int voteCount) {
    this.voteCount = voteCount;
  }

  public final static Parcelable.Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {

    @SuppressWarnings({
        "unchecked"
    })
    public MovieDetail createFromParcel(Parcel in) {
      MovieDetail instance = new MovieDetail();
      instance.adult = ((boolean) in.readValue((boolean.class.getClassLoader())));
      instance.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
      instance.belongsToCollection = ((BelongsToCollection) in.readValue((BelongsToCollection.class.getClassLoader())));
      instance.budget = ((int) in.readValue((int.class.getClassLoader())));
      in.readList(instance.genres, (Genre.class.getClassLoader()));
      instance.homepage = ((String) in.readValue((String.class.getClassLoader())));
      instance.id = ((int) in.readValue((int.class.getClassLoader())));
      instance.imdbId = ((String) in.readValue((String.class.getClassLoader())));
      instance.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
      instance.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
      instance.overview = ((String) in.readValue((String.class.getClassLoader())));
      instance.popularity = ((double) in.readValue((double.class.getClassLoader())));
      instance.posterPath = ((String) in.readValue((String.class.getClassLoader())));
      in.readList(instance.productionCompanies, (ProductionCompany.class.getClassLoader()));
      in.readList(instance.productionCountries, (ProductionCountry.class.getClassLoader()));
      instance.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
      instance.revenue = ((int) in.readValue((int.class.getClassLoader())));
      instance.runtime = ((int) in.readValue((int.class.getClassLoader())));
      in.readList(instance.spokenLanguages, (SpokenLanguage.class.getClassLoader()));
      instance.status = ((String) in.readValue((String.class.getClassLoader())));
      instance.tagline = ((String) in.readValue((String.class.getClassLoader())));
      instance.title = ((String) in.readValue((String.class.getClassLoader())));
      instance.video = ((boolean) in.readValue((boolean.class.getClassLoader())));
      instance.voteAverage = ((double) in.readValue((double.class.getClassLoader())));
      instance.voteCount = ((int) in.readValue((int.class.getClassLoader())));
      return instance;
    }

    public MovieDetail[] newArray(int size) {
      return (new MovieDetail[size]);
    }

  };

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(adult);
    dest.writeValue(backdropPath);
    dest.writeValue(belongsToCollection);
    dest.writeValue(budget);
    dest.writeList(genres);
    dest.writeValue(homepage);
    dest.writeValue(id);
    dest.writeValue(imdbId);
    dest.writeValue(originalLanguage);
    dest.writeValue(originalTitle);
    dest.writeValue(overview);
    dest.writeValue(popularity);
    dest.writeValue(posterPath);
    dest.writeList(productionCompanies);
    dest.writeList(productionCountries);
    dest.writeValue(releaseDate);
    dest.writeValue(revenue);
    dest.writeValue(runtime);
    dest.writeList(spokenLanguages);
    dest.writeValue(status);
    dest.writeValue(tagline);
    dest.writeValue(title);
    dest.writeValue(video);
    dest.writeValue(voteAverage);
    dest.writeValue(voteCount);
  }

  public int describeContents() {
    return 0;
  }

}
