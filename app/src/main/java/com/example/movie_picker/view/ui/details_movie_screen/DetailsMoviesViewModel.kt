package com.example.movie_picker.view.ui.details_movie_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movie_picker.firebase.FirestoreClass
import com.example.movie_picker.firebase.ReadData
import com.example.movie_picker.repositories.MovieRep
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailsMoviesViewModel @Inject constructor(
	application: Application,
	val repository: MovieRep
) : AndroidViewModel(application) {
	
	private val fireStore = FirestoreClass()
	
	var list: MutableLiveData<List<Map<String, String>>> = MutableLiveData()
	
	fun myMoviesIdList() = fireStore.getMyMoviesList(object : ReadData {
		override suspend fun readData(value: List<Map<String, String>>) {
			list.postValue(value)
		}
	})
}