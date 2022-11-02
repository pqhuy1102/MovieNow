package com.example.movienow.data.remote.service

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET()
    suspend fun getAllMovies(
        @Query("s") s:String,
        @Query("page")page:Int,
        @Query("apiKey")apiKey:String
    )

}