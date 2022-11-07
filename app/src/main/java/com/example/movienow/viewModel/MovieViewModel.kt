package com.example.movienow.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienow.data.remote.MovieRepository
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private var _moviesResponse = MutableLiveData<Resource<List<Movie>>>()
    val movieResponse: LiveData<Resource<List<Movie>>> = _moviesResponse

    fun getAllMovies() {
        viewModelScope.launch {
            _moviesResponse.postValue(movieRepository.getAllMovies())
        }
    }
}