package com.example.movienow.data.remote.service

import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/")
     fun getAllMovies(
        @Query("s") s:String,
        @Query("page")page:Int,
        @Query("apiKey")apiKey:String
    ) : Single<MovieResponse>

     @GET("/")
     fun getMovieDetail(
         @Query("i") i:String,
         @Query("apiKey")apiKey: String
     ) : Single<MovieDetail>
}