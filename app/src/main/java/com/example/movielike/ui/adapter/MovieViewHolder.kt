package com.example.movielike.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movielike.R
import com.example.movielike.data.model.Movie
import com.example.movielike.databinding.ListItemBinding
import com.squareup.picasso.Picasso

/**
 * Created by Benjamin Vouillon on 15,July,2020
 */
const val PURL = "https://image.tmdb.org/t/p/w185"

class MovieViewHolder(
    itemView: View,
    private val moviesViewHolderListener: MoviesViewHolderListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ListItemBinding.bind(itemView)
    private var movie: Movie? = null

    fun bind(pair: Pair<Movie, Boolean>) {

        this.movie = pair.first

        binding.apply {
            itemView.resources.apply {

                listTitle.text = movie!!.title

                listPopularity.text =
                    getString(R.string.itemPopularity, movie!!.popularity.toString())

                listRate.text =
                    getString(R.string.itemRate, movie!!.voteAverage.toString())

                Picasso.get().load(PURL + movie!!.posterPath)
                    .into(image)
            }
        }

        binding.likeBtn.setOnClickListener {
            moviesViewHolderListener?.onItemLiked(movie!!)
        }

        binding.image.setOnClickListener {
            moviesViewHolderListener.onDetailsRequested(it, movie!!)
        }

        if (pair.second) {
            binding.likeBtn.setImageResource(R.drawable.ic_favorite_black_18dp)
        } else {
            binding.likeBtn.setImageResource(R.drawable.ic_favorite_border_black_18dp)
        }
    }

    interface MoviesViewHolderListener {
        fun onItemLiked(movie: Movie)
        fun onDetailsRequested(
            view: View,
            movie: Movie
        )
    }
}