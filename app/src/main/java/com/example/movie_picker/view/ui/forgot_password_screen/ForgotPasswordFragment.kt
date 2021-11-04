package com.example.movie_picker.view.ui.forgot_password_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.movie_picker.R
import com.example.movie_picker.databinding.FragmentForgotPasswordBinding
import com.example.movie_picker.view.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : BaseFragment(R.layout.fragment_forgot_password) {
	
	private var _binding: FragmentForgotPasswordBinding? = null
	private val binding get() = _binding!!
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentForgotPasswordBinding.bind(view)
		
		binding.forgotPassSubmitBtn.setOnClickListener {
			val email = binding.forgotPassEmailInput.text.toString()
			if (email.isEmpty()) {
				toast("You have to write your email!")
			} else {
				FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener {
					Navigation.findNavController(requireView())
						.navigate(R.id.action_forgotPasswordFragment_to_authenticationFragment)
					toast("Email sent successfully to reset your password!")
				}
			}
			
		}
	}
	
}

