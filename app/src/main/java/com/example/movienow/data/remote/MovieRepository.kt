package com.example.movienow.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
import com.example.movienow.data.remote.service.MovieApiService
import com.example.movienow.utils.Constants
import com.example.movienow.utils.Resource
import com.example.movienow.utils.Status
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApiService:MovieApiService) {

    fun getAllMovies() :Single<MovieResponse>{
        return movieApiService.getAllMovies("harry", 1, Constants.API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getMovieDetail(movieId:String) : Single<MovieDetail>{
            return movieApiService.getMovieDetail(movieId , Constants.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

//    val publishSubject = PublishSubject.create<Single<MovieResponse>>()
//
//    private var _moviesResponse = MutableLiveData<List<Movie>>()
//    val movieResponse: LiveData<List<Movie>> = _moviesResponse
//
//    fun getAllMovies() {
//        publishSubject.subscribe(getAllMoviesObserver())
//        publishSubject.onNext(movieApiService.getAllMovies("harry", 1, Constants.API_KEY))
//    }
//
//    private fun getAllMoviesObserver() : Observer<Single<MovieResponse>> {
//        val movieObserver = object : Observer<Single<MovieResponse>>{
//            override fun onSubscribe(d: Disposable) {
//            }
//
//            override fun onError(e: Throwable) {
//            }
//
//            override fun onComplete() {
//            }
//
//            override fun onNext(t: Single<MovieResponse>) {
//
//            }
//        }
//
//        return movieObserver
//
//    }


}




