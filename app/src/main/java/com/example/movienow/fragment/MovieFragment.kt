package com.example.movienow.fragment

import android.annotation.SuppressLint
import android.app.SearchManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movienow.R
import com.example.movienow.adapter.MovieAdapter
import com.example.movienow.databinding.FragmentMovieBinding
import com.example.movienow.utils.Status
import com.example.movienow.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint //generates an individual Hilt component -> receive dependencies
class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding

    private lateinit var movieAdapter:MovieAdapter

    private lateinit var movieViewModel: MovieViewModel


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieAdapter = MovieAdapter(){
          val action = MovieFragmentDirections.actionMovieFragmentToDetailFragment(it.id)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvMovieList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }

        movieViewModel.networkStatusMovie.observe(viewLifecycleOwner){
            when (it.status){
                Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(activity, "Error ${it.message}", Toast.LENGTH_LONG).show()
                    binding.progressbar.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                    movieAdapter.updateMovies(it.data)
                }
            }
        }

        //handle search movie
        binding.searchView.onActionViewExpanded()
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                movieAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                movieAdapter.filter.filter(newText)
                return false
            }

        })
    }




}