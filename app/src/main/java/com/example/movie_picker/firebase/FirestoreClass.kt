package com.example.movie_picker.firebase

import android.app.Activity
import android.media.Rating
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
	
	var fireStoreInstance = fireStore.collection("users").document(currentUser()).collection("movies")
	
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
	
	fun addCollection(collectionName: String, title: String, map: MutableMap<String, Int>) {
		fireStore.collection("users").document(currentUser()).collection(collectionName)
			.document(title).set(map)
	}
	
	fun currentUser(): String {
		val currentUser = FirebaseAuth.getInstance().currentUser
		var currentUserId = ""
		if (currentUser != null) {
			currentUserId = currentUser.uid
		}
		return currentUserId
	}
	
	fun getMyMoviesList(readData: ReadData) {
		val movies = ArrayList<Map<String,String>>()
		val moviesRef = fireStore.collection("users").document(currentUser()).collection("movies")
		moviesRef.get().addOnCompleteListener { task ->
			GlobalScope.launch {
				if (task.isSuccessful) {
					for (document in task.result!!) {
						val id = document.data["id"].toString()
						val rating = document.data["rating"].toString()
						val status = document.data["status"].toString()
						Log.v("lol","sss$status")
						movies.add(mapOf(("rating" to rating),"id" to id, "watchingStatus" to status ))
					}
					readData.readData(movies)
				}
			}
		}
	}
	
}

interface ReadData {
	suspend fun readData(value: List<Map<String,String>>)
}