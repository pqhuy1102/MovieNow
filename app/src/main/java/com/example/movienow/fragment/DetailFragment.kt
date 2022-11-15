package com.example.movienow.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movienow.adapter.SimilarMoviesAdapter
import com.example.movienow.data.remote.request.RatingRequest
import com.example.movienow.databinding.FragmentDetailBinding
import com.example.movienow.utils.Status
import com.example.movienow.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var similarMovieAdapter: SimilarMoviesAdapter
    private var ratingValue: Double = 0.0
    private  var movieId: Int = 0

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        similarMovieAdapter = SimilarMoviesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieId = args.movieId
        movieViewModel.getMovieDetail(movieId)

        movieViewModel.networkStatusMovieDetail.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressbarMovieDetail.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(activity, "Error ${it.message}", Toast.LENGTH_LONG).show()
                    binding.progressbarMovieDetail.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressbarMovieDetail.visibility = View.GONE
                    binding.movieDetail = it.data
                }
            }
        }

        //handle get similar movie
        movieViewModel.networkStatusMovie.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    similarMovieAdapter.updateSimilarMovies(it.data)
                }
                Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        })

        //handle rating event
        binding.btnSubmit.setOnClickListener {
            ratingValue = binding.ratingBar.rating.toDouble()

            if(ratingValue != 0.0){
                handleRatingRequest()
                movieViewModel.ratingStatus.observe(viewLifecycleOwner) {
                    Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                }
            } else{
                Toast.makeText(activity, "Please rate before submit!", Toast.LENGTH_LONG).show()
            }
        }

        //handle similar movies
        binding.rcvSimilarMovies.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarMovieAdapter
        }

        //click poster to watch trailer on youtube
        binding.movieDetailImg.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=hf8EYbVxtCY")
                )
            )
        }

    }

    private fun handleRatingRequest() {
        movieViewModel.ratingMovie(RatingRequest(ratingValue), movieId)
    }

}