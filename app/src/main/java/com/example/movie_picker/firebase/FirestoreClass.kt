package com.example.movie_picker.firebase

import android.app.Activity
import android.util.Log
import androidx.paging.liveData
import com.example.movie_picker.data.models.MyMovieModel
import com.example.movie_picker.data.models.User
import com.example.movie_picker.data.rest.model.MovieForList
import com.example.movie_picker.view.ui.registration_screen.RegistrationFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirestoreClass {
	
	private val fireStore = FirebaseFirestore.getInstance()
	
	fun registerUser(regFr: RegistrationFragment, userId: User) {
		fireStore.collection("users")
			.document(userId.userId)
			.set(userId, SetOptions.merge())
			.addOnSuccessListener {
				regFr.onSuccessReg()
			}
			.addOnFailureListener { e ->
				Log.e("exception", "Error while registering the user $e")
			}
	}
	
	fun addCollection(uid: String, title: String, movieId: Int) {
		val movieIdMap:MutableMap<String, Int> = mutableMapOf("id" to movieId)
		fireStore.collection("users").document(uid).collection("movies").document(title).set(movieIdMap)
	}
	
	fun currentUser(): String {
		val currentUser = FirebaseAuth.getInstance().currentUser
		var currentUserId = ""
		if (currentUser != null) {
			currentUserId = currentUser.uid
		}
		return currentUserId
	}
	
	fun getMyMoviesList(readData: ReadData){
		val movies = ArrayList<String>()
		val moviesRef = fireStore.collection("users").document(currentUser()).collection("movies")
		moviesRef.get().addOnCompleteListener{ task ->
			GlobalScope.launch {
				if(task.isSuccessful){
					for (document in task.result!!) {
						val id = document.data["id"].toString()
						movies.add(id)
					}
					readData.readData(movies)
				}
			}
		}
	}
}

interface ReadData{
	suspend fun readData(value: List<String>)
}