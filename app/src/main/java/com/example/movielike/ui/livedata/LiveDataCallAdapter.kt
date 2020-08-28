package com.example.movielike.ui.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movielike.data.entities.internet.ApiResponse
import com.example.movielike.data.entities.internet.UNKNOWN_CODE
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * Created by Benjamin Vouillon on 17,July,2020
 */

class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun adapt(call: Call<R>): MutableLiveData<ApiResponse<R>> {
        return object : MutableLiveData<ApiResponse<R>>() {
            private var isSuccess = false

            override fun onActive() {
                super.onActive()
                if (!isSuccess) enqueue()
            }

            override fun onInactive() {
                super.onInactive()
                dequeue()
            }

            private fun dequeue() {
                if (call.isExecuted) call.cancel()
            }

            private fun enqueue() {
                call.enqueue(object : Callback<R> {
                    override fun onFailure(call: Call<R>, t: Throwable) {
                        postValue(
                            ApiResponse.create(
                                UNKNOWN_CODE,
                                t
                            )
                        )
                    }

                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        postValue(
                            ApiResponse.create(
                                response
                            )
                        )
                        isSuccess = true
                    }
                })
            }
        }
    }

    override fun responseType(): Type = responseType
}