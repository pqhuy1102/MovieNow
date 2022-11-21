package com.example.movienow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movienow.data.local.database.FavoriteMovie
import com.example.movienow.databinding.FavMovieItemBinding

class FavoriteMoviesAdapter :RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMovieViewHolder>() {
    private var favMovies = mutableListOf<FavoriteMovie>()
    private lateinit var deleteMovie: FavoriteMovie

    fun updateFavMovies(movies:List<FavoriteMovie>){
        this.favMovies = movies.toMutableList()
        notifyDataSetChanged()
    }

    fun deleteFavMovie (position: Int){
        deleteMovie = favMovies.removeAt(position)
        val removedMovieList = favMovies.filter {
            movie -> movie.id != deleteMovie.id
        }
        updateFavMovies(removedMovieList)
    }

    fun getDeleteMovie():FavoriteMovie{
        return deleteMovie
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

