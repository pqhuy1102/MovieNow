package com.example.movienow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.databinding.MovieItemBinding
import javax.inject.Inject

class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    var movies = mutableListOf<Movie>()

    fun updateMovies(movies:List<Movie>){
        this.movies = movies.toMutableList()
        notifyItemRangeInserted(0, movies.size)
    }

    class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =  MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.tvTitle.text = movie.Title
        holder.binding.rvYear.text = movie.Year

        Glide.with(holder.binding.imgMovieImage.context)
            .load(movie.Poster)
            .centerCrop()
            .into(holder.binding.imgMovieImage)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}