package com.example.movienow.data.remote.response

import com.example.movienow.data.remote.partial.Movie

data class MovieResponse(
    val Response: String,
    val Search: List<Movie>,
    val totalResults: String
)