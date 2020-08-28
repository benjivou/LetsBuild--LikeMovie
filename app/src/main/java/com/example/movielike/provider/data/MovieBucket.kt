package com.example.movielike.provider.data

import com.example.movielike.data.model.Movie

data class MovieBucket(
    var id: Int? = null,

    var popularity: Float? = null,

    var votePount: Int? = null,

    var video: Boolean? = null,

    var posterPath: String? = null,

    var adult: Boolean? = null,

    var backdropPath: String? = null,

    var originalLanguage: String? = null,

    var originalTitle: String? = null,

    var title: String? = null,

    var voteAverage: Float? = null,

    var overview: String? = null,

    var releaseDate: String? = null
) {
    constructor(movie: Movie) : this() {
        id = movie.id
        popularity = movie.popularity
        votePount = movie.votePount
        video = movie.video
        posterPath = movie.posterPath
        adult = movie.adult
        backdropPath = movie.backdropPath
        originalLanguage = movie.originalLanguage
        originalTitle = movie.originalTitle
        title = movie.title
        voteAverage = movie.voteAverage
        overview = movie.overview
        releaseDate = movie.releaseDate
    }

    fun convertToMovie(): Movie {
        return Movie(
            id = id,
            popularity = popularity,
            votePount = votePount,
            video = video,
            posterPath = posterPath,
            adult = adult,
            backdropPath = backdropPath,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            title = title,
            voteAverage = voteAverage,
            overview = overview,
            releaseDate = releaseDate
        )
    }

}



