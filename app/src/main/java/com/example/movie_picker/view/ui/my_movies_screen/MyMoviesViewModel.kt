package com.example.movie_picker.view.ui.my_movies_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movie_picker.data.models.MyMovieAllData
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
	
	
	var list: MutableLiveData<MutableList<MyMovieAllData>> = MutableLiveData()
	val listM: MutableList<MyMovieAllData> = mutableListOf()
	
	private val rating:MutableLiveData<String>? = null
	
	fun myMoviesList() = fireStore.getMyMoviesList(object : ReadData {
		override suspend fun readData(value: List<Map<String,String>>) {
			for (item in value) {
				listM.add(MyMovieAllData(item["rating"]!!, repository.getMovie(item["id"]!!.toInt()), item["watchingStatus"]!! ))
			}
			list.postValue(listM)
		}
	})
	
	fun getRatingString(title:String){
		fireStore.fireStoreInstance.document(title).get().addOnCompleteListener { task ->
			rating?.value = task.result?.getString("rating")!!
		}
	}
}