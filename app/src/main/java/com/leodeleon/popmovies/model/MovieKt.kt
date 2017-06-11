package com.leodeleon.popmovies.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "movies")
data class MovieKt(
    @PrimaryKey
    var id : Int,
    var title: String,
    var overview: String,
    var poster_path: String,
    var backdrop_path: String,
    var release_date: String,
    var vote_average: Double,
    var isFavorite: Int
) : Parcelable{
  companion object {
    @JvmField val CREATOR: Parcelable.Creator<MovieKt> = object : Parcelable.Creator<MovieKt> {
      override fun createFromParcel(source: Parcel): MovieKt = MovieKt(source)
      override fun newArray(size: Int): Array<MovieKt?> = arrayOfNulls(size)
    }
  }

  constructor(source: Parcel) : this(
  source.readInt(),
  source.readString(),
  source.readString(),
  source.readString(),
  source.readString(),
  source.readString(),
  source.readDouble(),
  source.readInt()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.writeInt(id)
    dest.writeString(title)
    dest.writeString(overview)
    dest.writeString(poster_path)
    dest.writeString(backdrop_path)
    dest.writeString(release_date)
    dest.writeDouble(vote_average)
    dest.writeInt(isFavorite)
  }
}