package com.example.movie_picker.di

import com.example.movie_picker.data.rest.api.MovieApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	
	@Provides
	@Singleton
	fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
		Retrofit.Builder()
			.baseUrl("https://api.themoviedb.org/")
			.client(okHttpClient)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.build()
	
	@Provides
	@Singleton
	fun provideOkkHttpClient(): OkHttpClient {
		val httpLoggingInterceptor = HttpLoggingInterceptor()
		httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
		return OkHttpClient.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.build()
	}
	
	@Provides
	@Singleton
	fun gson(): Gson = GsonBuilder().setLenient().create()
	
	
	@Provides
	@Singleton
	fun getService(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
}