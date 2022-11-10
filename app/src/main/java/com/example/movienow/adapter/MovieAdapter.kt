package com.example.movienow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.databinding.MovieItemBinding
import javax.inject.Inject


class MovieAdapter @Inject constructor(private val itemClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    private var movies = mutableListOf<Movie>()

    fun updateMovies(moviesList:List<Movie>?){
        this.movies = moviesList!!.toMutableList()
        this.notifyItemRangeInserted(0, moviesList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =  MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, this.itemClickListener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie:Movie, itemClickListener: (Movie) -> Unit){
            binding.movie = movie

            binding.root.setOnClickListener {
                itemClickListener(movie)
            }
        }
    }
}