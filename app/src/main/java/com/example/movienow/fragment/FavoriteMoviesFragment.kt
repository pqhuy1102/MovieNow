package com.example.movienow.fragment

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.movienow.R
import com.example.movienow.adapter.FavoriteMoviesAdapter
import com.example.movienow.databinding.FragmentFavoriteMoviesBinding
import com.example.movienow.utils.Status
import com.example.movienow.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteMoviesBinding
    private lateinit var favMoviesAdapter: FavoriteMoviesAdapter
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favMoviesAdapter = FavoriteMoviesAdapter()
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.rcvFavMovies.apply {
           layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
           adapter = favMoviesAdapter
       }

        //handle get fav movies from db
        movieViewModel.getAllFavoriteMovies()

        movieViewModel.getAllFavoriteMovieStatus.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING -> {
                    binding.pbFavMovie.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.pbFavMovie.visibility = View.GONE
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    binding.pbFavMovie.visibility = View.GONE
                    it.data?.let { it1 -> favMoviesAdapter.updateFavMovies(it1) }
                }
            }
        })

    }

}