package com.example.movienow.data.remote.service

import com.example.movienow.data.remote.request.RatingRequest
import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
import com.example.movienow.data.remote.response.RatingResponse
import com.example.movienow.data.remote.response.SimilarMoviesResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
     fun getAllMovies(
        @Query("page")page:Int
    ) : Single<MovieResponse>

     @GET("movie/{movie_id}")
     fun getMovieDetail(
         @Path("movie_id")id:Int
     ) : Observable<MovieDetail>

     @GET("movie/{movie_id}/similar")
     fun getSimilarMovie(
         @Path("movie_id")id:Int
     ):Single<SimilarMoviesResponse>

    @Headers("Content-Type: application/json")
    @POST("movie/{movie_id}/rating")
    fun ratingMovie(
        @Body values:RatingRequest,
        @Path("movie_id")id: Int,
    ):Single<RatingResponse>

}