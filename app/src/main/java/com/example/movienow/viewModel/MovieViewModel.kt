package com.example.movienow.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movienow.data.local.database.FavoriteMovie
import com.example.movienow.data.MovieRepository
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.data.remote.partial.SimilarMovie
import com.example.movienow.data.remote.request.RatingRequest
import com.example.movienow.data.remote.response.MovieDetail
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

    private var _getAllFavoriteMoviesStatus = MutableLiveData<Resource<List<FavoriteMovie>>>()
    val getAllFavoriteMovieStatus:LiveData<Resource<List<FavoriteMovie>>> = _getAllFavoriteMoviesStatus

    private var _isExistInFavoriteMovies = MutableLiveData<Boolean>()
    val isExistInFavoriteMovies:LiveData<Boolean> = _isExistInFavoriteMovies

    private var _isDeleteFavoriteMovieSuccessfull = MutableLiveData<Resource<Boolean>>()
    val isDeleteFavoriteMovieSuccessfull:LiveData<Resource<Boolean>> = _isDeleteFavoriteMovieSuccessfull

    //get all movies
    fun getAllMovies(page:Int) {
        movieRepository.getAllMoviesWithPublishSubject(page)
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
                _ratingStatus.postValue(it.status_message)
            },
            {
                _ratingStatus.postValue(it.message.toString())
            }
        )
    }

    fun getSimilarMovie(movieId: Int){
        movieRepository.getSimilarMoviesWithPublishSubject(movieId)
        movieRepository.getSimilarMovies().subscribe(
            {
                _networkStatusSimilarMovies.postValue(Resource(Status.SUCCESS, it.results, null))
            },
            {
                _networkStatusSimilarMovies.postValue(Resource(Status.ERROR, null, it.message.toString()))
            }
        )
    }

    fun saveFavoriteMovie(movie: FavoriteMovie){
        movieRepository.saveFavoriteMovies(movie)
            .subscribe({
                Log.i("MyTag", "Sc")
            },{
                Log.i("MyTag", it.message.toString())
            })
    }

    fun deleteFavoriteMovie(movieId: Int){
        movieRepository.deleteFavoriteMovie(movieId)
            .subscribe(
                {
                    _isDeleteFavoriteMovieSuccessfull.postValue(Resource(Status.SUCCESS, true, "Delete this movie successfully!"))
                },
                {
                    _isDeleteFavoriteMovieSuccessfull.postValue(Resource(Status.ERROR, true, "Delete this movie fail!"))
                }
            )
    }

    fun isMovieExistInFavorite(movieId: Int){
        movieRepository.isMovieExistInFavorite(movieId)
            .subscribe(
                {
                    _isExistInFavoriteMovies.value= it
                },{
                    Log.i("Check exist err", it.message.toString())
                }
            )
    }

    fun getAllFavoriteMovies(){
        movieRepository.getAllFavoriteMovies()
        movieRepository.getFavoriteMovies()
            .subscribe(
                {
                    _getAllFavoriteMoviesStatus.postValue(Resource(Status.SUCCESS, it, null))
                },
                {
                    _getAllFavoriteMoviesStatus.postValue(Resource(Status.ERROR, null, null))
                }
            )
    }
}


