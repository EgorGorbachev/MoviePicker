package com.example.movie_picker.data.rest.model

data class MovieResponse(
	val total_results:Int,
	val results: List<MovieForList>
)