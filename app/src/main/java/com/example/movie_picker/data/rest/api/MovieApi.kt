package com.example.movie_picker.data.rest.api

import com.example.movie_picker.BuildConfig
import com.example.movie_picker.data.rest.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {
	
	companion object {
		const val KEY = BuildConfig.API_KEY
	}
	
	@GET("3/movie/popular?api_key=$KEY")
	@Headers("Application: application/json")
	suspend fun getPopularMovie(
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	): MovieResponse
	
	@GET("3/movie/top_rated?api_key=$KEY")
	@Headers("Application: application/json")
	suspend fun getTopRatedMovie(
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	): MovieResponse
	
	@GET("3/movie/now_playing?api_key=$KEY")
	@Headers("Application: application/json")
	suspend fun getNowPlayingMovie(
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	): MovieResponse
	
	@GET("3/movie/upcoming?api_key=$KEY")
	@Headers("Application: application/json")
	suspend fun getUpcomingMovie(
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	): MovieResponse
}