package com.example.movielike.ui.livedata

import com.example.movielike.ui.livedata.LiveDataCallAdapter
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Benjamin Vouillon on 23,July,2020
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val observableType =
            CallAdapter.Factory.getParameterUpperBound(
                0,
                returnType as ParameterizedType
            ) as? ParameterizedType
                ?: throw IllegalArgumentException("resource must be parameterized")
        return LiveDataCallAdapter<Any>(
            getParameterUpperBound(
                0,
                observableType
            )
        )
    }
}