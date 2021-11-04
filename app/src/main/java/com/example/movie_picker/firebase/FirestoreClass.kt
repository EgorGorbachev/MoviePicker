package com.example.movie_picker.firebase

import android.app.Activity
import android.util.Log
import com.example.movie_picker.data.models.User
import com.example.movie_picker.data.rest.model.MovieForList
import com.example.movie_picker.view.ui.registration_screen.RegistrationFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class FirestoreClass {

	private val fireStore = FirebaseFirestore.getInstance()
	
	fun registerUser(regFr: RegistrationFragment, userId:User){
		fireStore.collection("users")
			.document(userId.userId)
			.set(userId, SetOptions.merge())
			.addOnSuccessListener {
				regFr.onSuccessReg()
			}
			.addOnFailureListener { e->
				Log.e("exception","Error while registering the user $e")
			}
	}
	
	fun addCollection(uid:String, title:String, movie:MovieForList){
		fireStore.collection("users").document(uid).collection("movies").document(title).set(movie)
	}
	
	fun currentUser():String{
		val currentUser = FirebaseAuth.getInstance().currentUser
		var currentUserId = ""
		if (currentUser != null){
			currentUserId =  currentUser.uid
		}
		return currentUserId
	}
}