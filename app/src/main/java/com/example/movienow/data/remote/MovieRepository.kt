package com.example.movienow.data.remote

import com.example.movienow.data.remote.request.RatingRequest
import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
import com.example.movienow.data.remote.response.RatingResponse
import com.example.movienow.data.remote.service.MovieApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApiService:MovieApiService) {

    fun getAllMovies() :Single<MovieResponse>{
        return movieApiService.getAllMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getMovieDetail(movieId: Int) : Single<MovieDetail>{
            return movieApiService.getMovieDetail(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun ratingMovie(movieId: Int, ratingValue:RatingRequest): Single<RatingResponse>{
        return movieApiService.ratingMovie( ratingValue,movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

}




