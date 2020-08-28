package com.example.movielike.data.util

import com.example.movielike.data.entities.internet.MoviesService
import com.example.movielike.ui.livedata.LiveDataCallAdapterFactory
import com.example.movielike.ui.viewmodel.URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Singleton{

    val service: MoviesService = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()
        .create(MoviesService::class.java)
}