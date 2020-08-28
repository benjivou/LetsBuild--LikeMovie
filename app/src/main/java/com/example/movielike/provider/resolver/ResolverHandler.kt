package com.example.movielike.provider.resolver

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.movielike.data.model.Movie
import com.example.movielike.provider.data.InitBucket
import com.google.gson.Gson
import com.google.gson.GsonBuilder

private const val TAG = "ResolverHandler"

class ResolverHandler {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun initProcessTo(packageName: String, context: Context): InitBucket {
            val uriString = "content://${packageName}/init"

            val cr = context.contentResolver

            val cursor =
                cr.query(
                    Uri.parse(uriString),
                    null,
                    null,
                    null
                )

            return cursor?.moveToFirst().run {
                Gson().fromJson<InitBucket>(cursor!!.getString(0), InitBucket::class.java)
            }

        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun recupAllMovieLiked(packageName: String, context: Context): List<Movie> {
            val uriString = "content://${packageName}.data"
            Log.i(TAG, "recupAllMovieLiked: $uriString")
            val cr = context.contentResolver
            val result = mutableListOf<Movie>()
            val cursor =
                cr.query(
                    Uri.parse(uriString),
                    null,
                    null,
                    null
                )


            cursor?.moveToFirst()?.run {
                if (!this) return result
                do {
                    result.add(
                        GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .create().fromJson<Movie>(
                                cursor?.getString(0) ?: break,
                                Movie::class.java
                            )
                    )
                    Log.i(TAG, "recupAllMovieLiked: content of : ${cursor?.getString(0)} ")
                } while (cursor?.moveToNext())
            } ?: return result
            result.forEach {Log.i(TAG, "recupAllMovieLiked: resutlat ${it.originalTitle} ")  }

            return result

        }
    }
}