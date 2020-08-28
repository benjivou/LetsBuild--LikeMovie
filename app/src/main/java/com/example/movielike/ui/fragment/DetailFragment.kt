package com.example.movielike.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.movielike.R
import com.example.movielike.data.entities.displayabledata.EmptyMoviePrepared
import com.example.movielike.data.entities.displayabledata.ErrorMoviePrepared
import com.example.movielike.data.entities.displayabledata.SuccessMoviePrepared
import com.example.movielike.databinding.DetailFragmentBinding
import com.example.movielike.ui.adapter.PURL
import com.example.movielike.ui.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso


/**
 * Created by Benjamin Vouillon on 23,July,2020
 */

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DetailFragmentBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailFragmentBinding.bind(view)
        val idMovie = args.StringAttributIdUser

        viewModel.getMovieAndIsLiked(idMovie)

        viewModel.currentMoviePair.observe(viewLifecycleOwner, Observer { moviePrepared ->
            binding.apply {
                when (moviePrepared) {
                    is SuccessMoviePrepared ->
                        moviePrepared.content.apply {
                            first.let { movie ->

                                title.text = movie.title
                                overview.text = movie.overview
                                Picasso.get().load(PURL + movie.posterPath).into(image)
                                userRating.text =
                                    resources.getString(
                                        R.string.itemRate,
                                        movie.voteAverage.toString()
                                    )
                                realeseDate.text = movie.releaseDate

                            }
                            if (second) {
                                likeBtn.setImageResource(R.drawable.ic_favorite_black_18dp)
                            } else {
                                likeBtn.setImageResource(R.drawable.ic_favorite_border_black_18dp)
                            }

                            likeBtn.setOnClickListener {
                                onLikeButtonClicked()
                            }
                        }
                    is ErrorMoviePrepared -> title.text = requireContext().getString(
                        R.string.errorInternetServeError,
                        moviePrepared.errorCode,
                        moviePrepared.errorMessage
                    )
                    is EmptyMoviePrepared -> title.text =
                        requireContext().getString(R.string.errorInternetVoidAnswer)
                }
            }
        })
    }

    private fun onLikeButtonClicked() {
        viewModel.likeOrUnlikeMovieExposed()
    }
}


