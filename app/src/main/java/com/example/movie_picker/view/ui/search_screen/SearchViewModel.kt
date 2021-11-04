package com.example.movie_picker.view.ui.search_screen

import android.app.Application
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movie_picker.repositories.MovieRep
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
	application: Application,
	repository: MovieRep
) : AndroidViewModel(application) {
	
	val popularMovies = repository.getPopularMovies().cachedIn(viewModelScope)
	
	val topRatedMovies = repository.getTopRatedMovies().cachedIn(viewModelScope)
	
	val nowPlayingMovies = repository.getNowPlayingMovies().cachedIn(viewModelScope)
	
	val upcomingMovies = repository.getUpcomingMovies().cachedIn(viewModelScope)
	
}