package com.example.movie_picker.view.ui.search_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_picker.R
import com.example.movie_picker.data.rest.model.MovieForList
import com.example.movie_picker.databinding.FragmentSearchBinding
import com.example.movie_picker.di.AppInterfaceImpl
import com.example.movie_picker.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search), SearchAdapter.OnItemClickListener,
	SearchAdapter.OnReadMoreClick {
	
	@Inject
	lateinit var app: AppInterfaceImpl
	
	private val viewModel by viewModels<SearchViewModel>()
	
	private var _binding: FragmentSearchBinding? = null
	private val binding get() = _binding!!
	
	private var menuShow = false
	
	private lateinit var viewRoot: ViewGroup
	
	private lateinit var recyclerView: RecyclerView
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentSearchBinding.bind(view)
		
		app.configurationRetrofit()
		
		viewRoot = binding.root
		
		val adapter = SearchAdapter(this, this)
		recyclerView = binding.moviesListRecyclerView
		val layoutManager = GridLayoutManager(requireContext(), 1)
		recyclerView.layoutManager = layoutManager
		recyclerView.adapter = adapter
		
		GlobalScope.launch(Dispatchers.Main) {
			viewModel.popularMovies.observe(viewLifecycleOwner) {
				adapter.submitData(viewLifecycleOwner.lifecycle, it)
			}
		}
		
		val menu = LayoutInflater.from(requireContext())
			.inflate(R.layout.change_list_menu, viewRoot, false)
		menu.findViewById<Button>(R.id.popularMoviesChangeBtn).setOnClickListener {
			viewModel.popularMovies.observe(viewLifecycleOwner) {
				adapter.submitData(viewLifecycleOwner.lifecycle, it)
			}
			updateView("Popular Movies")
		}
		menu.findViewById<Button>(R.id.topRatedMoviesChangeBtn).setOnClickListener {
			viewModel.topRatedMovies.observe(viewLifecycleOwner) {
				adapter.submitData(viewLifecycleOwner.lifecycle, it)
			}
			updateView("Top Rated Movies")
		}
		menu.findViewById<Button>(R.id.nowPlayingMoviesChangeBtn).setOnClickListener {
			viewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
				adapter.submitData(viewLifecycleOwner.lifecycle, it)
			}
			updateView("Now In Theatres")
		}
		menu.findViewById<Button>(R.id.upcomingMoviesChangeBtn).setOnClickListener {
			viewModel.upcomingMovies.observe(viewLifecycleOwner) {
				adapter.submitData(viewLifecycleOwner.lifecycle, it)
			}
			updateView("Upcoming In Theatres")
		}
		binding.changeListBtn.setOnClickListener {
			menuShow = if (!menuShow) {
				binding.searchHeaderContainer.addView(menu)
				rotationBtn()
				true
			} else {
				binding.searchHeaderContainer.removeViewAt(viewRoot.childCount - 1)
				rotationBtn()
				false
			}
		}
		
		binding.toMyMoviesBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_searchFragment_to_myMoviesFragment)
		}
		
		
		activity?.onBackPressedDispatcher?.addCallback(
			viewLifecycleOwner,
			object : OnBackPressedCallback(true) {
				override fun handleOnBackPressed() {
					minimizeApp()
				}
			})
	}
	
	private fun updateView(text: String) {
		GlobalScope.launch(Dispatchers.Main) {
			delay(200)
			recyclerView.scrollToPosition(0)
			binding.searchHeader.text = text
			binding.searchHeaderContainer.removeViewAt(viewRoot.childCount - 1)
			binding.changeListBtn
				.animate()
				.rotation(binding.changeListBtn.rotation + 180f)
				.interpolator =
				AccelerateDecelerateInterpolator()
			menuShow = false
		}
	}
	
	private fun rotationBtn() {
		binding.changeListBtn
			.animate()
			.rotation(binding.changeListBtn.rotation + 180f)
			.interpolator =
			AccelerateDecelerateInterpolator()
	}
	
	override fun onItemClick(movieForList: MovieForList) {
		Log.v(TAG, "item tap")
	}
	
	override fun onReadMoreClick(movieForList: MovieForList) {
		val action =
			SearchFragmentDirections.actionSearchFragmentToDetailsMovieFragment(movieForList)
		findNavController().navigate(action)
	}
}

