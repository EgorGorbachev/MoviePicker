package com.example.movie_picker.view.ui.authentication_screen

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.example.movie_picker.R
import com.example.movie_picker.data.prefs.REMEMBER_USER
import com.example.movie_picker.data.prefs.SharedPreferences
import com.example.movie_picker.databinding.FragmentAuthenticationBinding
import com.example.movie_picker.view.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationFragment : BaseFragment(R.layout.fragment_authentication) {
	
	private var _binding: FragmentAuthenticationBinding? = null
	private val binding get() = _binding!!
	
	var sp: SharedPreferences? = null
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentAuthenticationBinding.bind(view)
		
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
			
			when {
				emailText.isEmpty() -> toast("You have to write your email!")
				passwordText.isEmpty() -> toast("You have to write your password!")
				else -> {
					FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passwordText)
						.addOnCompleteListener { task ->
							if (task.isSuccessful) {
								toast("You logged successful!")
								
								Navigation.findNavController(requireView())
									.navigate(R.id.action_authenticationFragment_to_searchFragment)
								
							} else toast("Something wrong! Chek your email and password!")
						}
					
					if (binding.checkBoxRememberMeAuth.isChecked) {
						sp?.setPref(REMEMBER_USER, true)
					} else sp?.setPref(REMEMBER_USER, false)
				}
			}
		}
		
		binding.toForgotPasswordTextBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_authenticationFragment_to_forgotPasswordFragment)
		}
		
		activity?.onBackPressedDispatcher?.addCallback(
			viewLifecycleOwner,
			object : OnBackPressedCallback(true) {
				override fun handleOnBackPressed() {
					minimizeApp()
				}
			})
	}
	
}