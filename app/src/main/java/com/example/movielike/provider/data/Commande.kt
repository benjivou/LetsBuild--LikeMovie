package com.example.movielike.provider.data

import android.os.Looper
import androidx.lifecycle.LiveData

private const val TIME_TO_REFRESH: Long = 1000

class Commande<T>(val action: () -> T) : LiveData<T>() {

    val handler = android.os.Handler(Looper.getMainLooper())

    override fun onActive() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                value = action()
                handler.postDelayed(this, TIME_TO_REFRESH)
            }
        }, TIME_TO_REFRESH)
    }

    override fun onInactive() {
        handler.removeCallbacks { }
    }

}