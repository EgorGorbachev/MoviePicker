package com.example.movie_picker.view.ui.my_movies_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movie_picker.data.models.MyMovieModel
import com.example.movie_picker.firebase.FirestoreClass
import com.example.movie_picker.firebase.ReadData
import com.example.movie_picker.repositories.MovieRep
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MyMoviesViewModel @Inject constructor(
	application: Application,
	private val repository: MovieRep
) : AndroidViewModel(application) {
	
	private val fireStore = FirestoreClass()
	
	
	var list: MutableLiveData<MutableList<MyMovieModel>> = MutableLiveData()
	val listM: MutableList<MyMovieModel> = mutableListOf()
	
	fun myMoviesList() = fireStore.getMyMoviesList(object : ReadData {
		override suspend fun readData(value: List<String>) {
			for (item in value) {
				listM.add(repository.getMovie(item.toInt()))
			}
			list.postValue(listM)
		}
	})
	
}