package com.example.movielike.data.entities.wrapper

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults


// This works for results of any RealmModel in your project
class RealmLiveData<T : RealmModel?>(realmResults: RealmResults<T>) :
    LiveData<RealmResults<T>?>() {
    private val results: RealmResults<T> = realmResults
    private val listener: RealmChangeListener<RealmResults<T>?> =
        RealmChangeListener { results -> value = results }

    override fun onActive() {
        results.addChangeListener(listener)
    }

    override fun onInactive() {
        results.removeChangeListener(listener)
    }
}