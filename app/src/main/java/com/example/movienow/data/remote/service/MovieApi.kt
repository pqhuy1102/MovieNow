package com.example.movienow.data.remote.service

import com.example.movienow.data.remote.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/")
    suspend fun getAllMovies(
        @Query("s") s:String,
        @Query("page")page:Int,
        @Query("apiKey")apiKey:String
    ) : Response<MovieResponse>

}