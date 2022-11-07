package com.example.movienow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.databinding.MovieItemBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class MovieAdapter () : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    var movies = mutableListOf<Movie>()

    fun updateMovies(movies:List<Movie>?){
        this.movies = movies!!.toMutableList()
        notifyItemRangeInserted(0, movies.size)
    }

    class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie:Movie){
            binding.movie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =  MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies?.get(position)
        if (movie != null) {
            holder.bind(movie)
        }
//        holder.binding.tvTitle.text = movie.Title
//        holder.binding.rvYear.text = movie.Year
//
//        Glide.with(holder.binding.imgMovieImage.context)
//            .load(movie.Poster)
//            .centerCrop()
//            .into(holder.binding.imgMovieImage)
    }

    override fun getItemCount(): Int {
        return movies!!.size
    }
}