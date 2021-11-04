package com.example.movie_picker.data.rest.api.paging_resources

import androidx.paging.PagingSource
import com.example.movie_picker.data.rest.api.MovieApi
import com.example.movie_picker.data.rest.model.MovieForList
import retrofit2.HttpException
import java.io.IOException

class PagingSourceUpcoming(
	private val movieApi: MovieApi,
) : PagingSource<Int, MovieForList>() {
	
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieForList> {
		val position = params.key ?: MOVIE_STARTING_PAGE_INDEX
		
		return try {
			val response = movieApi.getUpcomingMovie(page = position, perPage = params.loadSize)
			val movie = response.results
			
			LoadResult.Page(
				data = movie,
				prevKey = if (position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
				nextKey = if (movie.isEmpty()) null else position + 1
			)
		} catch (exception: IOException) {
			LoadResult.Error(exception)
		} catch (exception: HttpException) {
			LoadResult.Error(exception)
		} catch (exception: NullPointerException) {
			LoadResult.Error(exception)
		}
		
	}
	
}