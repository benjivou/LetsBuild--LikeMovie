package com.example.movielike.ui.viewmodel


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.movielike.data.dao.MovieDAO
import com.example.movielike.data.entities.displayabledata.EmptyMoviePrepared
import com.example.movielike.data.entities.displayabledata.ErrorMoviePrepared
import com.example.movielike.data.entities.displayabledata.MoviePrepared
import com.example.movielike.data.entities.displayabledata.SuccessMoviePrepared
import com.example.movielike.data.entities.internet.ApiEmptyResponse
import com.example.movielike.data.entities.internet.ApiErrorResponse
import com.example.movielike.data.entities.internet.ApiSuccessResponse
import com.example.movielike.data.model.Movie
import com.example.movielike.data.model.TypeDisplay
import com.example.movielike.data.util.Singleton.service
import com.example.movielike.provider.data.Commande
import com.example.movielike.provider.resolver.ResolverHandler


/**
 * Created by Benjamin Vouillon on 08,July,2020
 */

const val URL = "https://api.themoviedb.org/3/movie/"

private const val TAG = "MainViewModel"

class MainViewModel() : ViewModel() {

    private val movieDAO = MovieDAO()


    var likedList: Commande<List<Movie>>? = null


    /**
     * Type of the list displayed
     */
    private val _typeDisplay: MutableLiveData<TypeDisplay> =
        MutableLiveData(TypeDisplay.POPULAR)

    val currentTypeDisplay: TypeDisplay
        get() = _typeDisplay.value!!


    private var movieList =
        Transformations.switchMap<TypeDisplay, MoviePrepared<List<Pair<Movie, Boolean>>>>(
            this._typeDisplay
        )
        {
            when (_typeDisplay.value) {

                TypeDisplay.LIKED -> Transformations.map(movieDAO.getAllMovies()) {
                    convertMoviesToSuccessMoviesPrepared(it)
                }
                TypeDisplay.LIKED_POPULAR -> Transformations.map(movieDAO.getAllByPopular()) {
                    convertMoviesToSuccessMoviesPrepared(it)
                }
                TypeDisplay.LIKED_RATED -> Transformations.map(movieDAO.getAllByRated()) {

                    convertMoviesToSuccessMoviesPrepared(it)
                }
                else -> internetCall()
            }
        }

    // List of Movies ready to be displayed
    private var _currentList =
        MediatorLiveData<MoviePrepared<List<Pair<Movie, Boolean>>>>()
    val currentList: LiveData<MoviePrepared<List<Pair<Movie, Boolean>>>>
        get() {
            init()
            return _currentList
        }

    private fun init() {

        _currentList.removeSource(likedList!!)
        _currentList.removeSource(movieList)

        _currentList.addSource(likedList!!) { listMovies ->
            val bufM = movieList.value
            if (bufM is SuccessMoviePrepared<List<Pair<Movie, Boolean>>>) {
                _currentList.value = SuccessMoviePrepared(bufM.content.map {
                    Pair(it.first, listMovies.contains(it.first))
                })
            }
        }
        Log.d(TAG, "initialisation of your mainviewmodel: ")

        _currentList.addSource(movieList) { listMoviePrepared ->
            Log.d(TAG, "movie list is changed: ")
            _currentList.value =
                if (listMoviePrepared is SuccessMoviePrepared<List<Pair<Movie, Boolean>>>) {

                    SuccessMoviePrepared(listMoviePrepared.content.map {
                        Pair(it.first, likedList?.value?.contains(it.first) ?: false)
                    })
                } else {
                    Log.i(TAG, "list movie is not a succesMoviePrepared: ")
                    listMoviePrepared
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initContext(context: Context) {
        likedList ?: run {
            likedList =
                Commande { ResolverHandler.recupAllMovieLiked("com.example.movieapp", context) }
        }
    }

    fun getList(typeDisplay: TypeDisplay) {
        this._typeDisplay.value = typeDisplay
    }


    private fun internetCall(): LiveData<MoviePrepared<List<Pair<Movie, Boolean>>>> =
        Transformations.map(service.listOfMovies(_typeDisplay.value!!.s)) {
            when (it) {
                is ApiSuccessResponse -> SuccessMoviePrepared(it.body.results.map { movie ->
                    Pair(movie, false)
                })
                is ApiEmptyResponse -> {
                    EmptyMoviePrepared<List<Pair<Movie, Boolean>>>()
                }
                is ApiErrorResponse -> {
                    ErrorMoviePrepared(it.errorCode, it.errorMessage)
                }
            }
        }

    fun likeOrUnlikeMovie(movie: Movie) {

        movieDAO.likeOrUnlikeMovie(
            movie,
            this.likedList?.value!!.contains(movie)
        )

    }

    private fun convertMoviesToSuccessMoviesPrepared(movies: List<Movie>): SuccessMoviePrepared<List<Pair<Movie, Boolean>>> {
        val res = mutableListOf<Pair<Movie, Boolean>>()
        movies.forEach { movie -> res.add(Pair(movie, true)) }
        return SuccessMoviePrepared(res.toList())
    }
}