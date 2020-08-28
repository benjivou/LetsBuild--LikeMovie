package com.example.movielike.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey



/**
 * Created by Benjamin Vouillon on 08,July,2020
 */

open class Movie(
    @Expose @PrimaryKey @SerializedName("id") var id: Int? = null,
    @Expose @SerializedName("popularity") var popularity: Float? = null,
    @Expose @SerializedName("vote_pount") var votePount: Int? = null,
    @Expose @SerializedName("video") var video: Boolean? = null,
    @Expose @SerializedName("poster_path") var posterPath: String? = null,
    @Expose @SerializedName("adult") var adult: Boolean? = null,
    @Expose @SerializedName("backdrop_path") var backdropPath: String? = null,
    @Expose @SerializedName("original_language") var originalLanguage: String? = null,
    @Expose @SerializedName("original_title") var originalTitle: String? = null,
    @Expose @SerializedName("title") var title: String? = null,
    @Expose @SerializedName("vote_average") var voteAverage: Float? = null,
    @Expose @SerializedName("overview") var overview: String? = null,
    @Expose @SerializedName("release_date") var releaseDate: String? = null
) : RealmObject() {

    override fun equals(other: Any?): Boolean {
        if (other is Movie)
            if (id == other.id)
                return true
        return super.equals(other)
    }
}

