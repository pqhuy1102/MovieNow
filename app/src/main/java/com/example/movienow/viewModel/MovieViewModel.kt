package com.example.movienow.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienow.data.remote.MovieRepository
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
import com.example.movienow.utils.Resource
import com.example.movienow.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor( // tell Hilt how to provide instances of this class
    private val movieRepository: MovieRepository
) : ViewModel() {
    private var _networkStatusMovie = MutableLiveData<Resource<List<Movie>>>()
    val networkStatusMovie: LiveData<Resource<List<Movie>>> = _networkStatusMovie

    private var _networkStatusMovieDetail = MutableLiveData<Resource<MovieDetail>>()
    val networkStatusMovieDetail: LiveData<Resource<MovieDetail>> = _networkStatusMovieDetail

    init {
        movieRepository.getAllMovies()
            .subscribe(
                fun(movies: MovieResponse) {
                    _networkStatusMovie.value = Resource(Status.SUCCESS, movies.Search,null)
                }
            ) { e -> _networkStatusMovie.value = Resource(Status.ERROR, null,e.message.toString()) }
    }

    fun getMovieDetail(movieId:String) {
        movieRepository.getMovieDetail(movieId)
            .subscribe(
                fun(movieDetail: MovieDetail){
                    _networkStatusMovieDetail.value = Resource(Status.SUCCESS, movieDetail, null )
                }
            ) { e -> _networkStatusMovieDetail.value = Resource(Status.ERROR, null,e.message.toString()) }

    }
}


