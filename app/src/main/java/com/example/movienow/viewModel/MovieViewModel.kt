package com.example.movienow.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movienow.data.remote.MovieRepository
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.data.remote.request.RatingRequest
import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
import com.example.movienow.data.remote.response.RatingResponse
import com.example.movienow.utils.Resource
import com.example.movienow.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor( // tell Hilt how to provide instances of this class
    private val movieRepository: MovieRepository
) : ViewModel() {
    private var _networkStatusMovie = MutableLiveData<Resource<List<Movie>>>()
    val networkStatusMovie: LiveData<Resource<List<Movie>>> = _networkStatusMovie

    private var _networkStatusMovieDetail = MutableLiveData<Resource<MovieDetail>>()
    val networkStatusMovieDetail: LiveData<Resource<MovieDetail>> = _networkStatusMovieDetail

    private var _ratingStatus = MutableLiveData<String>()
    val ratingStatus:LiveData<String> = _ratingStatus

    //get all movies
    init {
        movieRepository.getAllMovies()
            .subscribe(
                fun(movies: MovieResponse){
                    _networkStatusMovie.value = Resource(Status.SUCCESS, movies.results, null )
                }
            ) { e -> _networkStatusMovie.value = Resource(Status.ERROR, null,e.message.toString()) }
    }

    fun getMovieDetail(movieId:Int) {
        movieRepository.getMovieDetail(movieId)
            .subscribe(
                fun(movieDetail: MovieDetail){
                    _networkStatusMovieDetail.value = Resource(Status.SUCCESS, movieDetail, null )
                }
            ) { e -> _networkStatusMovieDetail.value = Resource(Status.ERROR, null,e.message.toString()) }

    }

    fun ratingMovie(ratingValue:RatingRequest , movieId: Int){
        movieRepository.ratingMovie(movieId, ratingValue)
            .subscribe(
                fun(status: RatingResponse){
                    _ratingStatus.value = status.status_message
                }
            ){ e -> _ratingStatus.value = e.message.toString() }
    }
}


