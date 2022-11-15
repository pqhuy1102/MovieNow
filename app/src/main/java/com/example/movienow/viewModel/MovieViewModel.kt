package com.example.movienow.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movienow.data.remote.MovieRepository
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.data.remote.partial.SimilarMovie
import com.example.movienow.data.remote.request.RatingRequest
import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.RatingResponse
import com.example.movienow.utils.Resource
import com.example.movienow.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private var _networkStatusMovie = MutableLiveData<Resource<List<Movie>>>()
    val networkStatusMovie: LiveData<Resource<List<Movie>>> = _networkStatusMovie

    private var _networkStatusMovieDetail = MutableLiveData<Resource<MovieDetail>>()
    val networkStatusMovieDetail: LiveData<Resource<MovieDetail>> = _networkStatusMovieDetail

    private var _ratingStatus = MutableLiveData<String>()
    val ratingStatus:LiveData<String> = _ratingStatus

    private var _networkStatusSimilarMovies = MutableLiveData<Resource<List<SimilarMovie>>>()
    val networkStatusSimilarMovie:LiveData<Resource<List<SimilarMovie>>> = _networkStatusSimilarMovies

    //get all movies
    init {
        movieRepository.getAllMoviesWithPublishSubject()
        movieRepository.getMoviesSubject().subscribe(
            {
                _networkStatusMovie.postValue(Resource(Status.SUCCESS, it.results, null))
            },
            {
                _networkStatusMovie.postValue(Resource(Status.ERROR, null, it.message.toString()))
            }
        )
    }

    fun getMovieDetail(movieId:Int) {
        movieRepository.getMovieDetailWithPublishProcessor(movieId)
        movieRepository.getMovieDetail().subscribe(
            {
                _networkStatusMovieDetail.postValue(Resource(Status.SUCCESS, it, null))
            },
            {
                _networkStatusMovieDetail.postValue(Resource(Status.ERROR, null, it.message.toString()))
            }
        )

    }

    fun ratingMovie(ratingValue:RatingRequest , movieId: Int){
        movieRepository.ratingMovieWithBehaviorSubject(ratingValue, movieId)
        movieRepository.getRatingMovie().subscribe(
            {
                _ratingStatus.postValue(it.status_message.toString())
            },
            {
                _ratingStatus.postValue(it.message.toString())
            }
        )
    }

    fun getSimilarMovie(movieId: Int){
        movieRepository.getSimilarMoviesWithBehaviorSubject(movieId)
        movieRepository.getSimilarMovies().subscribe(
            {
                _networkStatusSimilarMovies.postValue(Resource(Status.SUCCESS, it.results, null))
            },
            {
                _networkStatusSimilarMovies.postValue(Resource(Status.ERROR, null, it.message.toString()))
            }
        )
    }
}


