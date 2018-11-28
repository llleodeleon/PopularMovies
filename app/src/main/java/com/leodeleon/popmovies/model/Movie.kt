package com.leodeleon.popmovies.model

import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.leodeleon.popmovies.feature.common.AdapterConstants
import com.leodeleon.popmovies.feature.common.ViewType
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movies", primaryKeys = ["id"])
@Parcelize
data class Movie(
    var id : Int,
    var title: String,
    var overview: String,
    var poster_path: String,
    var backdrop_path: String,
    var release_date: String,
    var vote_average: Double,
    var favorite: Int
): ViewType, Parcelable {
  override fun getViewType(): Int {
    return AdapterConstants.MOVIES
  }

  fun isFavorite() = favorite == 1
}

data class MovieResponse(
    var page: Int,
    var results: List<Movie>,
    var total_results: Int,
    var total_pages: Int
)