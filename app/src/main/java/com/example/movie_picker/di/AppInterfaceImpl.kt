package com.example.movie_picker.di

import com.example.movie_picker.data.rest.api.MovieApi
import retrofit2.Retrofit
import javax.inject.Inject

class AppInterfaceImpl @Inject constructor(
	private val retrofit: Retrofit
) : AppInterface {
	
	lateinit var movieApi: MovieApi
	
	override fun configurationRetrofit() {
		movieApi = retrofit.create(MovieApi::class.java)
	}
	
}