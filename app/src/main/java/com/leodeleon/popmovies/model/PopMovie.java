package com.leodeleon.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class PopMovie implements Parcelable {
  private int id;

  private MovieDetail movieDetail;

  private List<Video> videos;

  public PopMovie(int id){
    this.id = id;
  }

  protected PopMovie(Parcel in) {
    id = in.readInt();
    movieDetail = in.readParcelable(MovieDetail.class.getClassLoader());
    videos = in.createTypedArrayList(Video.CREATOR);
  }

  public static final Creator<PopMovie> CREATOR = new Creator<PopMovie>() {
    @Override public PopMovie createFromParcel(Parcel in) {
      return new PopMovie(in);
    }

    @Override public PopMovie[] newArray(int size) {
      return new PopMovie[size];
    }
  };

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public MovieDetail getMovieDetail() {
    return movieDetail;
  }

  public void setMovieDetail(MovieDetail movieDetail) {
    this.movieDetail = movieDetail;
  }

  public List<Video> getVideos() {
    return videos;
  }

  public void setVideos(List<Video> videos) {
    this.videos = videos;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(movieDetail, flags);
    dest.writeInt(id);
    dest.writeTypedList(videos);
  }
}
