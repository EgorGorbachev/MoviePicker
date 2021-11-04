package com.example.movie_picker.view.ui.my_movies_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.movie_picker.R
import com.example.movie_picker.databinding.FragmentMyMoviesBinding
import com.example.movie_picker.view.base.BaseFragment

class MyMoviesFragment : BaseFragment(R.layout.fragment_my_movies) {
	
	private var _binding: FragmentMyMoviesBinding? = null
	private val binding get() = _binding!!
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentMyMoviesBinding.bind(view)
		
		binding.myMoviesSettingsBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_myMoviesFragment_to_settingsFragment)
		}
		
		binding.myMoviesBackBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_myMoviesFragment_to_searchFragment)
		}
		
	}
}