package com.leodeleon.popmovies.model

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.leodeleon.popmovies.feature.common.AdapterConstants
import com.leodeleon.popmovies.feature.common.ViewType

@Entity(tableName = "movies", primaryKeys = arrayOf("id"))
data class Movie(
    var id : Int = 0,
    var title: String = "",
    var overview: String = "",
    var poster_path: String = "",
    var backdrop_path: String = "",
    var release_date: String = "",
    var vote_average: Double = 0.0,
    var favorite: Int = 0
): ViewType, Parcelable {


  override fun getViewType(): Int {
    return AdapterConstants.MOVIES
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
      override fun createFromParcel(source: Parcel): Movie = Movie(source.readInt(),
          source.readString(),
          source.readString(),
          source.readString(),
          source.readString(),
          source.readString(),
          source.readDouble(),
          source.readInt())
      override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
    }
  }

  fun isFavorite() = favorite == 1

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.writeInt(id)
    dest.writeString(title)
    dest.writeString(overview)
    dest.writeString(poster_path)
    dest.writeString(backdrop_path)
    dest.writeString(release_date)
    dest.writeDouble(vote_average)
    dest.writeInt(favorite)
  }
}

data class MovieResponse(
    var page: Int,
    var results: List<Movie>,
    var total_results: Int,
    var total_pages: Int
)