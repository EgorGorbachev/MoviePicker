package com.example.movie_picker.view.ui.registration_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import com.example.movie_picker.R
import com.example.movie_picker.data.models.User
import com.example.movie_picker.data.prefs.REMEMBER_USER
import com.example.movie_picker.data.prefs.SharedPreferences
import com.example.movie_picker.databinding.FragmentRegistrationBinding
import com.example.movie_picker.firebase.FirestoreClass
import com.example.movie_picker.view.base.BaseFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {
	
	private var _binding: FragmentRegistrationBinding? = null
	private val binding get() = _binding!!
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentRegistrationBinding.bind(requireView())
		
		val sp = SharedPreferences(requireContext())
		
		binding.toLoginTextBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_registrationFragment_to_authenticationFragment)
			hideKeyboard()
		}
		
		binding.registerBtn.setOnClickListener {
			hideKeyboard()
			val emailText = binding.regEmailInput.text
			val passText = binding.regPasswordInput.text
			val repeatPassText = binding.regRepeatPasswordInput.text
			when {
				emailText.isEmpty() -> toast("You should write email!")
				passText.isEmpty() -> toast("You should write password!")
				passText.length < 6 -> toast("Password must be 6 or more symbols!")
				repeatPassText.isEmpty() -> toast("You should repeat password!")
				repeatPassText.toString() != passText.toString() -> toast("Your passwords don`t match!")
				else -> {
					
					FirebaseAuth.getInstance().createUserWithEmailAndPassword(
						emailText.toString().trim(),
						passText.toString().trim()
					).addOnCompleteListener(
						OnCompleteListener<AuthResult> { task ->
							if (task.isSuccessful) {
								val firebaseUser: FirebaseUser = task.result!!.user!!
								val fireStore = FirestoreClass()
								fireStore.registerUser(this, User(firebaseUser.uid, firebaseUser.email!!))
								Navigation.findNavController(requireView())
									.navigate(R.id.action_registrationFragment_to_searchFragment)
							} else toast(task.exception.toString())
						}
					)
					if (binding.checkBoxRememberMeReg.isChecked) {
						sp.setPref(REMEMBER_USER, true)
					} else sp.setPref(REMEMBER_USER, false)
				}
			}
		}
	}
	
	fun onSuccessReg() {
		toast("You were registered successfully!")
	}
	
	override fun onStop() {
		super.onStop()
		
		hideKeyboard()
	}
}