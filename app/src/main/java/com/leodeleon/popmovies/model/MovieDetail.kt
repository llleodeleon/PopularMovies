package com.leodeleon.popmovies.model

/**
 * Created by leodeleon on 08/02/2017.
 */

data class MovieDetail(
    var id: Int,
    var adult: Boolean,
    var backdrop_path: String,
    var belongs_to_collection: BelongsToCollection,
    var budget: Int,
    var genres: List<Genre>,
    var homepage: String,
    var imdb_id: String,
    var original_language: String,
    var original_title: String,
    var overview: String,
    var popularity: Double,
    var poster_path: String,
    var production_companies: List<ProductionCompany>,
    var production_countries: List<ProductionCountry>,
    var release_date: String,
    var revenue: Int,
    var runtime: Int,
    var spoken_languages: List<SpokenLanguage>,
    var status: String,
    var tagline: String,
    var title: String,
    var video: Boolean,
    var vote_average: Double,
    var vote_count: Int = 0
    )

data class BelongsToCollection(
    var id: Int,
    var name: String,
    var poster_path: String,
    var backdrop_path: String
)

data class Genre(
    var id: Int,
    var name: String)

data class ProductionCompany(
    var name: String,
    var id: Int
)

data class ProductionCountry(
    var iso_3166_1: String,
    var name: String
)

data class SpokenLanguage(
    var iso_639_1: String,
    var name: String
)