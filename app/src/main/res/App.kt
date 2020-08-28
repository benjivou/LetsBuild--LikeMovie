package com.example.movielike

import android.app.Application
import io.realm.Realm


/**
 * Created by Benjamin Vouillon on 15,July,2020
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}