package com.example.movielike.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import android.util.Log
import com.example.movielike.provider.config.APP_PRIORITY
import com.example.movielike.provider.config.SDK_VERSION
import com.example.movielike.provider.data.InitBucket
import com.google.gson.Gson

private const val TAG = "ExampleProvider"

class ExampleProvider : ContentProvider() {


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
            return when (p0.path) {
                "/init" -> onInit()
                else -> null
            }
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

    fun onInit(): Cursor {
        val toto = arrayOf("objet")

        return MatrixCursor(toto).also {
            it.addRow(arrayOf(Gson().toJson(InitBucket(SDK_VERSION, APP_PRIORITY))))
            Log.i(TAG, "onInit: $it")
        }
    }

    fun onRoleSend() {
        TODO()
    }
}