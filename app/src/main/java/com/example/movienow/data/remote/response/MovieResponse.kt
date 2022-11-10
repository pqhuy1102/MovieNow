package com.example.movienow.data.remote.response

import com.example.movienow.data.remote.partial.Movie

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)