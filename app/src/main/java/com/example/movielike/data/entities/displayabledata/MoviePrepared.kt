package com.example.movielike.data.entities.displayabledata

sealed class MoviePrepared<T>()

class EmptyMoviePrepared<T> : MoviePrepared<T>()
data class ErrorMoviePrepared<T>(val errorCode: Int, val errorMessage: String) : MoviePrepared<T>()
data class SuccessMoviePrepared<T>(var content: T) : MoviePrepared<T>()