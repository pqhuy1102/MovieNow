package com.example.movienow.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movienow.R
import com.example.movienow.adapter.MovieAdapter
import com.example.movienow.data.local.AppSharePreferences
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.databinding.FragmentMovieBinding
import com.example.movienow.utils.Status
import com.example.movienow.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable

@AndroidEntryPoint //generates an individual Hilt component -> receive dependencies
class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieAdapter:MovieAdapter
    private lateinit var movieViewModel: MovieViewModel
    private val disposables = CompositeDisposable()
    private var movieList: List<Movie>? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieAdapter = MovieAdapter {
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

        movieViewModel.getAllMovies()
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
                    movieList = it.data
                }
            }
        }

        //handle search movie
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

        //handle change theme
        checkTheme()

        //handle select type movies
        val spinner = binding.spinnerFilter
        ArrayAdapter.createFromResource(requireActivity(), R.array.Types, R.layout.custom_spinner_item).also {
                adapter -> adapter.setDropDownViewResource(R.layout.custom_spinner_item)
                spinner.adapter = adapter
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when (pos) {
                    0 -> movieViewModel.getAllMovies()
                    1 -> {
                        movieAdapter.updateMovies(movieList?.sortedByDescending {
                            it.release_date
                        })
                    }
                    else -> {
                        movieAdapter.updateMovies(movieList?.sortedByDescending {
                            it.vote_average
                        })
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun checkTheme() {
        when(AppSharePreferences(requireActivity()).darkMode){
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            2-> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}