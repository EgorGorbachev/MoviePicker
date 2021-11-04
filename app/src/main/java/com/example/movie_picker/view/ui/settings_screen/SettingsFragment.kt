package com.example.movie_picker.view.ui.settings_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.movie_picker.R
import com.example.movie_picker.data.prefs.REMEMBER_USER
import com.example.movie_picker.data.prefs.SharedPreferences
import com.example.movie_picker.databinding.FragmentSettingsBinding
import com.example.movie_picker.view.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
	
	private var _binding: FragmentSettingsBinding? = null
	private val binding get() = _binding!!
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentSettingsBinding.bind(view)
		
		val sp = SharedPreferences(requireContext())
		
		binding.settingsBackBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_settingsFragment_to_myMoviesFragment)
		}
		
		binding.singOutBtn.setOnClickListener {
			FirebaseAuth.getInstance().signOut()
			Navigation.findNavController(requireView())
				.navigate(R.id.action_settingsFragment_to_authenticationFragment)
			sp.setPref(REMEMBER_USER, false)
		}
	}
	
}