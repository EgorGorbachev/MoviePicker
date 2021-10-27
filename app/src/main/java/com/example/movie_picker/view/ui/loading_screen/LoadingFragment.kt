package com.example.movie_picker.view.ui.loading_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.movie_picker.R
import com.example.movie_picker.data.prefs.REMEMBER_USER
import com.example.movie_picker.data.prefs.SharedPreferences
import com.example.movie_picker.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.annotation.Native

@AndroidEntryPoint
class LoadingFragment : BaseFragment(R.layout.fragment_loading) {
	
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		
		val sp = SharedPreferences(requireContext())
		
		GlobalScope.launch(Dispatchers.Main) {
			delay(3000)
			if (sp.getPrefBool(REMEMBER_USER)==true){
				Navigation.findNavController(requireView()).navigate(R.id.action_loadingFragment_to_searchFragment)
			} else {
				Navigation.findNavController(requireView()).navigate(R.id.action_loadingFragment_to_authenticationFragment)
			}
		}
		
		return super.onCreateView(inflater, container, savedInstanceState)
	}
}