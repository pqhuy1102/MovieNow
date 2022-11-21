package com.example.movienow.data


import com.example.movienow.data.local.database.FavoriteMovie
import com.example.movienow.data.local.database.FavoriteMovieDao
import com.example.movienow.data.remote.request.RatingRequest
import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
import com.example.movienow.data.remote.response.RatingResponse
import com.example.movienow.data.remote.response.SimilarMoviesResponse
import com.example.movienow.data.remote.service.MovieApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService:MovieApiService,
    private val favoriteMovieDao: FavoriteMovieDao
) {
    private val moviesList = PublishSubject.create<MovieResponse>()
    private val movieDetail = PublishProcessor.create<MovieDetail>()
    private val ratingMovie = BehaviorProcessor.create<RatingResponse>()
    private val similarMovies = PublishSubject.create<SimilarMoviesResponse>()
    private val favoriteMovies = BehaviorSubject.create<List<FavoriteMovie>>()

    fun getAllMoviesWithPublishSubject(page: Int){
      movieApiService.getAllMovies(page)
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

    fun getSimilarMoviesWithPublishSubject(movieId: Int){
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

    fun saveFavoriteMovies(movie:FavoriteMovie):Completable{
        return favoriteMovieDao.insertFavoriteMovie(movie)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getAllFavoriteMovies(){
        favoriteMovieDao.getAllFavoritesMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    favoriteMovies.onNext(it)
                },
                {
                    favoriteMovies.onError(it)
                }
            )
    }

    fun deleteFavoriteMovie(movieId:Int) : Completable{
        return favoriteMovieDao.deleteFavoriteMovie(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun isMovieExistInFavorite(movieId: Int) : Single<Boolean>{
        return favoriteMovieDao.isMovieExist(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
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

    fun getFavoriteMovies(): BehaviorSubject<List<FavoriteMovie>>{
        return favoriteMovies
    }

    fun getAllMoviesWithSingle() :Single<MovieResponse>{
        return movieApiService.getAllMovies(1)
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





