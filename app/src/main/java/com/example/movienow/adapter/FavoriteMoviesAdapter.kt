package com.example.movienow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movienow.data.local.database.FavoriteMovie
import com.example.movienow.databinding.FavMovieItemBinding

class FavoriteMoviesAdapter :RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMovieViewHolder>() {

    private var favMovies = mutableListOf<FavoriteMovie>()

    fun updateFavMovies(movies:List<FavoriteMovie>){
        this.favMovies = movies.toMutableList()
        notifyDataSetChanged()
    }

    class FavoriteMovieViewHolder(private val binding: FavMovieItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: FavoriteMovie){
            binding.favMovie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding = FavMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val favMovie = favMovies[position]
        holder.bind(favMovie)
    }

    override fun getItemCount(): Int {
        return favMovies.size
    }
}