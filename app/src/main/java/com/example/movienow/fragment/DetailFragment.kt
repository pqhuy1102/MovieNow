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
import com.example.movienow.data.remote.request.RatingRequest
import com.example.movienow.databinding.FragmentDetailBinding
import com.example.movienow.utils.Status
import com.example.movienow.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var movieViewModel: MovieViewModel
    private var ratingValue: Double = 0.0
    private  var movieId: Int = 0

    private val args: DetailFragmentArgs by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

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

        movieViewModel.networkStatusMovieDetail.observe(viewLifecycleOwner, Observer {
            when(it.status){
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
        })

        //handle rating event
        binding.btnSubmit.setOnClickListener {
            ratingValue = binding.ratingBar.rating.toDouble()
            handleRatingRequest()
            movieViewModel.ratingStatus.observe(viewLifecycleOwner) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
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