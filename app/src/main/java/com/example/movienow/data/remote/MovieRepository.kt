package com.example.movienow.data.remote

import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
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

//
//    private var _networkStatusMovie = MutableLiveData<Resource<List<Movie>>>()
//    val networkStatusMovie: LiveData<Resource<List<Movie>>> = _networkStatusMovie
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
//                t.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                        fun(movies: MovieResponse) {
//                            Log.i("Movies", movies.totalResults)
//                    _networkStatusMovie.value = Resource(Status.SUCCESS, movies.Search,null)
//                }
//            ) { e -> _networkStatusMovie.value = Resource(Status.ERROR, null,e.message.toString()) }
//
//            }
//        }
//
//        return movieObserver
//
//    }


}




