package com.example.movienow.data.remote

import com.example.movienow.data.remote.partial.Movie
import com.example.movienow.data.remote.service.MovieApiService
import com.example.movienow.utils.Constants
import com.example.movienow.utils.Resource
import com.example.movienow.utils.Status
import javax.inject.Inject

class MovieRepository @Inject constructor (private val movieApiService:MovieApiService) {
    suspend fun getAllMovies() : Resource<List<Movie>>{
        return try {
            val response = movieApiService.getAllMovies("Harry", 1, Constants.API_KEY)
            if(response.isSuccessful){
                Resource(Status.SUCCESS, response.body()!!.Search, null)
            } else{
                Resource(Status.ERROR, null, null)
            }
        } catch (e: Exception){
            Resource(Status.ERROR, null, null)
        }
    }
}