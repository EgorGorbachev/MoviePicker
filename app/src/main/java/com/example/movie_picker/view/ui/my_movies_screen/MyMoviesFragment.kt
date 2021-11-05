package com.example.movie_picker.view.ui.my_movies_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_picker.R
import com.example.movie_picker.data.models.MyMovieModel
import com.example.movie_picker.databinding.FragmentMyMoviesBinding
import com.example.movie_picker.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyMoviesFragment : BaseFragment(R.layout.fragment_my_movies), MyMoviesAdapter.OnItemClickListener {
	
	private val viewModel by viewModels<MyMoviesViewModel>()
	
	private var _binding: FragmentMyMoviesBinding? = null
	private val binding get() = _binding!!
	
	private lateinit var recyclerView: RecyclerView
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentMyMoviesBinding.bind(view)
		
		val adapter = MyMoviesAdapter(this)
		recyclerView = binding.myMoviesRecyclerView
		val layoutManager = GridLayoutManager(requireContext(), 1)
		recyclerView.layoutManager = layoutManager
		recyclerView.adapter = adapter
		
		viewModel.myMoviesList()
		
		GlobalScope.launch(Dispatchers.Main) {
			viewModel.list.observe(viewLifecycleOwner){
				adapter.submitList(it)
			}
		}
		
		binding.myMoviesSettingsBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_myMoviesFragment_to_settingsFragment)
		}
		
		binding.myMoviesBackBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_myMoviesFragment_to_searchFragment)
		}
		
	}
	
	override fun onItemClick(myMovieModel: MyMovieModel) {
		toast("YAAAAAS!")
	}
}