package com.example.movielike.ui.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielike.R
import com.example.movielike.data.entities.displayabledata.EmptyMoviePrepared
import com.example.movielike.data.entities.displayabledata.ErrorMoviePrepared
import com.example.movielike.data.entities.displayabledata.SuccessMoviePrepared
import com.example.movielike.data.model.Movie
import com.example.movielike.data.model.TypeDisplay
import com.example.movielike.databinding.FragmentMainBinding
import com.example.movielike.ui.adapter.ListAdapter
import com.example.movielike.ui.adapter.MovieViewHolder
import com.example.movielike.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

private const val TAG = "MainFragment"

class MainFragment : Fragment(), MovieViewHolder.MoviesViewHolderListener {
    private lateinit var adapterList: ListAdapter
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        FragmentMainBinding.inflate(inflater, container, false).root

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initContext(requireContext())
        adapterList = ListAdapter(
            requireContext(),
            this
        )
        // setup the RecyclerView
        listRecyclerView.apply {
            layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.numberspan))
            // init the adapter to the good value
            adapter = adapterList
        }
        viewModel!!
            .currentList
            .observe(viewLifecycleOwner, Observer { movies ->
                when (movies) {
                    is SuccessMoviePrepared<List<Pair<Movie, Boolean>>> -> {
                        titleList.text = converteTypeDisplayToTitle(viewModel!!.currentTypeDisplay)
                        adapterList.changeData(movies.content)
                        errorText.visibility = View.GONE
                        titleList.visibility = View.VISIBLE
                        listRecyclerView.visibility = View.VISIBLE
                    }
                    is ErrorMoviePrepared<List<Pair<Movie, Boolean>>> ->
                        displayError(
                            requireContext().getString(
                                R.string.errorInternetServeError,
                                movies.errorCode, movies.errorMessage
                            )
                        )
                    is EmptyMoviePrepared<List<Pair<Movie, Boolean>>> ->
                        displayError(requireContext().getString(R.string.errorInternetVoidAnswer))
                }
            })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_action_bar, menu)
    }

    /**
     * the idea is to the current list display and get the new list
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // ignore likes
        if (item.itemId == R.id.displayMoviesLiked) return true
        // change the list in the modelView
        loadPage(
            when (item.itemId) {
                R.id.displayMoviesPopular -> TypeDisplay.POPULAR
                R.id.displayMoviesMostRated -> TypeDisplay.RATED
                R.id.displayMoviesLikedMostPopular -> TypeDisplay.LIKED_POPULAR
                R.id.displayMoviesLikedMostRated -> TypeDisplay.LIKED_RATED
                else -> TypeDisplay.POPULAR
            }
        )
        return true
    }

    private fun loadPage(typeDisplay: TypeDisplay) {
        viewModel!!.getList(
            typeDisplay
        )
        titleList.text = converteTypeDisplayToTitle(typeDisplay)
    }

    override fun onItemLiked(movie: Movie) {
        viewModel!!.likeOrUnlikeMovie(movie)
    }

    override fun onDetailsRequested(
        view: View,
        movie: Movie
    ) {
        Log.d(TAG, "onDetailsRequested: image is clicked")
        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id!!)
        view.findNavController().navigate(action)
    }

    private fun displayError(message: String) {
        errorText.text = message
        errorText.visibility = View.VISIBLE
        titleList.visibility = View.INVISIBLE
        listRecyclerView.visibility = View.INVISIBLE
    }

    private fun converteTypeDisplayToTitle(typeDisplay: TypeDisplay): String {
        return getString(
            when (typeDisplay) {
                TypeDisplay.POPULAR -> R.string.popularListTitle
                TypeDisplay.RATED -> R.string.ratedListTitle
                TypeDisplay.LIKED -> R.string.likedListTitle
                TypeDisplay.LIKED_POPULAR -> R.string.likedPopularListTitle
                TypeDisplay.LIKED_RATED -> R.string.likedRatedListTitle
            }
        )
    }
}