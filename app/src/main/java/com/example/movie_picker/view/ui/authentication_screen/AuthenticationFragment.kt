package com.example.movie_picker.view.ui.authentication_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.movie_picker.R
import com.example.movie_picker.data.prefs.REMEMBER_USER
import com.example.movie_picker.data.prefs.SharedPreferences
import com.example.movie_picker.data.prefs.USER_ID
import com.example.movie_picker.databinding.FragmentAuthenticationBinding
import com.example.movie_picker.view.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment : BaseFragment(R.layout.fragment_authentication) {
	
	private var _binding: FragmentAuthenticationBinding? = null
	private val binding get() = _binding!!
	
	var sp: SharedPreferences? = null
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentAuthenticationBinding.bind(requireView())
		
		sp = SharedPreferences(requireContext())
		
		binding.toRegisterTextBtn.setOnClickListener {
			hideKeyboard()
			Navigation.findNavController(requireView())
				.navigate(R.id.action_authenticationFragment_to_registrationFragment)
		}
		
		binding.authLoginBtn.setOnClickListener {
			hideKeyboard()
			val emailText = binding.authEmailInput.text.toString().trim()
			val passwordText = binding.authPasswordInput.text.toString().trim()
			
			FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener { task ->
				if (task.isSuccessful) {
					toast("You logged successful!")
					
					Navigation.findNavController(requireView()).navigate(R.id.action_authenticationFragment_to_searchFragment)
					
				} else toast("Something wrong!" + task.exception.toString())
			}
			
			if (binding.checkBoxRememberMeAuth.isChecked){
				sp?.setPref(REMEMBER_USER, true)
			} else sp?.setPref(REMEMBER_USER, false)
		}
	}
}