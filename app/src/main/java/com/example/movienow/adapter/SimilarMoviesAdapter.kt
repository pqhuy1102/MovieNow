package com.example.movienow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.databinding.SimilarMovieItemBinding

class SimilarMoviesAdapter : RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMovieViewHolder>() {
    private var similarMovies = mutableListOf<Movie>()

    fun updateSimilarMovies(similarMovies: List<Movie>?){
        this.similarMovies = similarMovies!!.toMutableList()
        notifyDataSetChanged()
    }

    class SimilarMovieViewHolder(private val binding:SimilarMovieItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(similarMovie: Movie){
            binding.similarMovie = similarMovie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        val binding = SimilarMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        val similarMovie = similarMovies[position]
        holder.bind(similarMovie)
    }

    override fun getItemCount(): Int {
        return similarMovies.size
    }
}