package com.example.movielike.data.model

import com.example.movielike.data.model.Movie
import com.google.gson.annotations.SerializedName

/**
 * Created by Benjamin Vouillon on 08,July,2020
 */
data class ResultPage(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<Movie>
)