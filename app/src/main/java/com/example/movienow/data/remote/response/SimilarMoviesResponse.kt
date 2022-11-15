package com.example.movienow.data.remote.response

import com.example.movienow.data.remote.partial.SimilarMovie

data class SimilarMoviesResponse(
    val page: Int,
    val results: List<SimilarMovie>,
    val total_pages: Int,
    val total_results: Int
)