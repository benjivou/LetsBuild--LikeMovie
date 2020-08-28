package com.example.movielike.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.example.movielike.data.dao.MovieDAO
import com.example.movielike.data.model.Movie
import com.example.movielike.provider.data.MovieBucket
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.Realm


private const val TAG = "LikeProvider"

class LikeProvider : ContentProvider() {

    var realm: MovieDAO

    init {
        Realm.init(requireContext())
        realm = MovieDAO()
    }


    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        Log.i(TAG, "query: ${p0.path}")
        return onRequest()

    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented") // TODO sent list
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    private fun onRequest(): Cursor {

        val result = MatrixCursor(arrayOf("movies"))
        Realm.getDefaultInstance().where(Movie::class.java).findAll().forEach {
            val toto: Movie = it
            Log.i(TAG, "onRequest: $toto")
            Log.i(
                TAG, "onRequest: ${
                    GsonBuilder()
                        .create().toJson(MovieBucket(toto))
                }"
            )
            result.addRow(arrayOf(Gson().toJson(MovieBucket(toto))))
        }
        return result
    }
}



