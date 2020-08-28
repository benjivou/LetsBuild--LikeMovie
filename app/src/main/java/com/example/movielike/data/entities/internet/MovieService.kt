package com.example.movielike.data.entities.internet

import androidx.lifecycle.MutableLiveData

import com.example.movielike.data.model.Movie
import com.example.movielike.data.model.ResultPage
import com.example.movielike.data.untracked.KEY_PRIVATE

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Benjamin Vouillon on 08,July,2020
 */

interface MoviesService {

    @GET("{type}")
    fun listOfMovies(
        @Path("type") type: String,
        @Query("api_key") s: String = KEY_PRIVATE
    ): MutableLiveData<ApiResponse<ResultPage>>

    @GET("{idMovie}")
    fun movieById(
        @Path("idMovie") idMovie: String,
        @Query("api_key") s: String = KEY_PRIVATE
    ): MutableLiveData<ApiResponse<Movie>>
}

