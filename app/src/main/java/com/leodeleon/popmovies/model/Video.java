package com.leodeleon.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by leodeleon on 10/02/2017.
 */

public class Video implements Parcelable {
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("iso_639_1")
  @Expose
  private String iso6391;
  @SerializedName("iso_3166_1")
  @Expose
  private String iso31661;
  @SerializedName("key")
  @Expose
  private String key;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("site")
  @Expose
  private String site;
  @SerializedName("size")
  @Expose
  private int size;
  @SerializedName("type")
  @Expose
  private String type;

  private Video(Parcel in) {
    id = in.readString();
    iso6391 = in.readString();
    iso31661 = in.readString();
    key = in.readString();
    name = in.readString();
    site = in.readString();
    size = in.readInt();
    type = in.readString();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(iso6391);
    dest.writeString(iso31661);
    dest.writeString(key);
    dest.writeString(name);
    dest.writeString(site);
    dest.writeInt(size);
    dest.writeString(type);
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<Video> CREATOR = new Creator<Video>() {
    @Override public Video createFromParcel(Parcel in) {
      return new Video(in);
    }

    @Override public Video[] newArray(int size) {
      return new Video[size];
    }
  };

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIso6391() {
    return iso6391;
  }

  public void setIso6391(String iso6391) {
    this.iso6391 = iso6391;
  }

  public String getIso31661() {
    return iso31661;
  }

  public void setIso31661(String iso31661) {
    this.iso31661 = iso31661;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
