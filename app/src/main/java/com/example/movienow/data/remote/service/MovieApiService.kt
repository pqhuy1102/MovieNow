package com.example.movienow.data.remote.service

import com.example.movienow.data.remote.response.MovieDetail
import com.example.movienow.data.remote.response.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
     fun getAllMovies(
    ) : Single<MovieResponse>

     @GET("movie/{movie_id}")
     fun getMovieDetail(
         @Path("movie_id")id:Int
     ) : Single<MovieDetail>
}