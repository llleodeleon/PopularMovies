package com.leodeleon.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre implements Parcelable {

  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("name")
  @Expose
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(id);
    dest.writeValue(name);
  }

  public int describeContents() {
    return 0;
  }

  public final static Parcelable.Creator<Genre> CREATOR = new Creator<Genre>() {
    @SuppressWarnings({
        "unchecked"
    })
    public Genre createFromParcel(Parcel in) {
      Genre instance = new Genre();
      instance.id = ((int) in.readValue((int.class.getClassLoader())));
      instance.name = ((String) in.readValue((String.class.getClassLoader())));
      return instance;
    }

    public Genre[] newArray(int size) {
      return (new Genre[size]);
    }

  };

}
