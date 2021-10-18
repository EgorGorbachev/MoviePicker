package com.example.movie_picker.view.ui.loading_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.movie_picker.R
import com.example.movie_picker.view.base.BaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.annotation.Native

class LoadingFragment : BaseFragment(R.layout.fragment_loading) {
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		
		GlobalScope.launch {
			delay(3000)
			Navigation.findNavController(requireView()).navigate(R.id.action_loadingFragment_to_authenticationFragment)
		}
		
		return super.onCreateView(inflater, container, savedInstanceState)
	}
}