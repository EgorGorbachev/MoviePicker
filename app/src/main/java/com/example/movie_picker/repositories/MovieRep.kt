package com.example.movie_picker.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.movie_picker.data.rest.api.MovieApi
import com.example.movie_picker.data.rest.api.paging_resources.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRep @Inject constructor(
	private val movieApi: MovieApi
) {
	fun getPopularMovies() =
		Pager(
			config = PagingConfig(
				pageSize = 20,
				maxSize = 100,
				enablePlaceholders = false
			),
			pagingSourceFactory = { PagingSourceForPopular(movieApi) }
		).liveData
	
	fun getTopRatedMovies() =
		Pager(
			config = PagingConfig(
				pageSize = 20,
				maxSize = 100,
				enablePlaceholders = false
			),
			pagingSourceFactory = { PagingSourceForTopRated(movieApi) }
		).liveData
	
	fun getNowPlayingMovies() =
		Pager(
			config = PagingConfig(
				pageSize = 20,
				maxSize = 100,
				enablePlaceholders = false
			),
			pagingSourceFactory = { PagingSourceNowPlaying(movieApi) }
		).liveData
	
	fun getUpcomingMovies() =
		Pager(
			config = PagingConfig(
				pageSize = 20,
				maxSize = 100,
				enablePlaceholders = false
			),
			pagingSourceFactory = { PagingSourceUpcoming(movieApi) }
		).liveData
	
}