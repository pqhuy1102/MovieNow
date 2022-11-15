package com.example.movienow.data.remote


import com.example.movienow.data.remote.request.RatingRequest
import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
import com.example.movienow.data.remote.response.RatingResponse
import com.example.movienow.data.remote.response.SimilarMoviesResponse
import com.example.movienow.data.remote.service.MovieApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApiService:MovieApiService) {
    private val moviesList = PublishSubject.create<MovieResponse>()
    private val movieDetail = PublishProcessor.create<MovieDetail>()
    private val ratingMovie = BehaviorProcessor.create<RatingResponse>()
    private val similarMovies = PublishSubject.create<SimilarMoviesResponse>()

    fun getAllMoviesWithPublishSubject(){
      movieApiService.getAllMovies()
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe({
              moviesList.onNext(it)
          }, {
              moviesList.onError(it)
          })
    }

    fun getMovieDetailWithPublishProcessor(movieId: Int){
        movieApiService.getMovieDetail(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    movieDetail.onNext(it)
                },
                {
                    movieDetail.onError(it)
                }
            )
    }

    fun getSimilarMoviesWithBehaviorSubject(movieId: Int){
        movieApiService.getSimilarMovie(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    similarMovies.onNext(it)
                },
                {
                    similarMovies.onError(it)
                }
            )
    }

    fun ratingMovieWithBehaviorSubject( ratingValue: RatingRequest, movieId: Int){
        movieApiService.ratingMovie(ratingValue, movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                ratingMovie.onNext(it)
            }, {
                ratingMovie.onError(it)
            })
    }

    fun getMoviesSubject() : PublishSubject<MovieResponse>{
        return moviesList
    }

    fun getMovieDetail():PublishProcessor<MovieDetail>{
        return movieDetail
    }

    fun getRatingMovie():BehaviorProcessor<RatingResponse>{
        return ratingMovie
    }

    fun getSimilarMovies(): PublishSubject<SimilarMoviesResponse>{
        return similarMovies
    }

    fun getAllMoviesWithSingle() :Single<MovieResponse>{
        return movieApiService.getAllMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getMovieDetailWithObservable(movieId: Int) : Observable<MovieDetail>{
        return movieApiService.getMovieDetail(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun ratingMovieWithSingle(movieId: Int, ratingValue:RatingRequest): Single<RatingResponse>{
        return movieApiService.ratingMovie( ratingValue,movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}





