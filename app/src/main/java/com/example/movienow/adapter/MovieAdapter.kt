package com.example.movienow.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.databinding.MovieItemBinding
import javax.inject.Inject


class MovieAdapter(private val itemClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Filterable{
    private var movies = mutableListOf<Movie>()
    private var moviesFilter = mutableListOf<Movie>()

    fun updateMovies(moviesList:List<Movie>?){
        this.movies = moviesList!!.toMutableList()
        this.moviesFilter = this.movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =  MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesFilter[position]
        holder.bind(movie, this.itemClickListener)
    }

    override fun getItemCount(): Int {
        return moviesFilter.size
    }

    class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie:Movie, itemClickListener: (Movie) -> Unit){
            binding.movie = movie

            binding.root.setOnClickListener {
                itemClickListener(movie)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(char: CharSequence?): FilterResults {
                val charString = char?.toString() ?: ""
                moviesFilter = if(charString.isEmpty()) movies
                else {
                    val filterList = mutableListOf<Movie>()
                    movies.filter {
                        (it.title.contains(char!!))
                    }.forEach{
                        filterList.add(it)
                    }
                    filterList
                }

                return FilterResults().apply { values = moviesFilter }
            }

            override fun publishResults(char: CharSequence?, res: FilterResults?) {
                moviesFilter = if(res?.values == null){
                    ArrayList()
                } else{
                    res.values as MutableList<Movie>
                }

                notifyDataSetChanged()
            }

        }
    }
}