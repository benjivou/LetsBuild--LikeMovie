package com.example.movielike.data.model

/**
 * Created by Benjamin Vouillon on 17,July,2020
 */

/***
 * Represent the type of movie list qe want for this call
 */
enum class TypeDisplay(val s: String) {
    POPULAR("popular"),
    RATED("top_rated"),
    LIKED("liked"),
    LIKED_POPULAR("liked_popular"),
    LIKED_RATED("liked_rated")
}
