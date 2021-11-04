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
	
	@GET("3/movie/popular?")
	@Headers("Application: application/json")
	suspend fun getPopularMovie(
		@Query("api_key")key:String = KEY,
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	): MovieResponse
	
	@GET("3/movie/top_rated?")
	@Headers("Application: application/json")
	suspend fun getTopRatedMovie(
		@Query("api_key")key:String = KEY,
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	): MovieResponse
	
	@GET("3/movie/now_playing?")
	@Headers("Application: application/json")
	suspend fun getNowPlayingMovie(
		@Query("api_key")key:String = KEY,
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	): MovieResponse
	
	@GET("3/movie/upcoming?")
	@Headers("Application: application/json")
	suspend fun getUpcomingMovie(
		@Query("api_key")key:String = KEY,
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	): MovieResponse
	
}