package com.example.movie_picker.view.ui.my_movies_screen

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_picker.R
import com.example.movie_picker.data.models.MyMovieAllData
import com.example.movie_picker.databinding.FragmentMyMoviesBinding
import com.example.movie_picker.firebase.FirestoreClass
import com.example.movie_picker.view.base.BaseFragment
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyMoviesFragment : BaseFragment(com.example.movie_picker.R.layout.fragment_my_movies),
	MyMoviesAdapter.OnItemClickListener, MyMoviesAdapter.OnRatingClickListener,
	MyMoviesAdapter.OnWatchingStatusClickListener {
	
	private val viewModel by viewModels<MyMoviesViewModel>()
	
	private var _binding: FragmentMyMoviesBinding? = null
	private val binding get() = _binding!!
	
	private lateinit var recyclerView: RecyclerView
	private val mFirestoreClass = FirestoreClass()
	
	private lateinit var spinnerAdapter: ArrayAdapter<String>
	
	private val statusList: MutableList<String> = mutableListOf("Not watched", "Watched")
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentMyMoviesBinding.bind(view)
		
		val adapter = MyMoviesAdapter(this, this, this)
		recyclerView = binding.myMoviesRecyclerView
		val layoutManager = GridLayoutManager(requireContext(), 1)
		recyclerView.layoutManager = layoutManager
		recyclerView.adapter = adapter
		
		viewModel.myMoviesList()
		
		GlobalScope.launch(Dispatchers.Main) {
			viewModel.list.observe(viewLifecycleOwner) {
				adapter.submitList(it)
			}
		}
		
		binding.myMoviesSettingsBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(com.example.movie_picker.R.id.action_myMoviesFragment_to_settingsFragment)
		}
		
		binding.myMoviesBackBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(com.example.movie_picker.R.id.action_myMoviesFragment_to_searchFragment)
		}
	}
	
	private fun changingRating(myMovieAllData: MyMovieAllData, rating: Float) {
		toast(rating.toString())
		val fireStore = FirebaseFirestore.getInstance().collection("users")
			.document(mFirestoreClass.currentUser()).collection("movies")
			.document(myMovieAllData.details.title)
		fireStore.update("rating", rating.toString())
	}
	
	
	//////////////////////////////////Recycler items actions//////////////////////////////////
	
	override fun onItemClick(myMovieAllData: MyMovieAllData) {}
	
	override fun onRatingClick(
		myMovieAllData: MyMovieAllData,
		rating: Float,
		spinner: Spinner,
		container: ConstraintLayout,
		ratingView: RatingBar
	) {
		changingRating(myMovieAllData, rating)
		if (spinner.selectedItem == statusList[0] && rating != 0.0f) {
			spinnerStatusUpdate(myMovieAllData, 1, container, ratingView)
			spinner.post(Runnable { spinner.setSelection(1) })
		}
	}
	
	override fun initSpinnerAdapter(
		myMovieAllData: MyMovieAllData,
		spinner: Spinner,
		container: ConstraintLayout
	) {
		spinnerAdapter = ArrayAdapter<String>(
			requireContext(),
			R.layout.spinner_row,
			statusList
		)
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		spinner.adapter = spinnerAdapter
		when (myMovieAllData.watchingStatus) {
			statusList[0] -> {
				spinner.post(Runnable { spinner.setSelection(0) })
			}
			statusList[1] -> {
				container.setBackgroundColor(
					ContextCompat.getColor(
						requireContext(),
						R.color.tertiary_lite_color
					)
				)
				spinner.post(Runnable { spinner.setSelection(1) })
			}
		}
	}
	
	override fun spinnerStatusUpdate(
		myMovieAllData: MyMovieAllData,
		position: Int,
		container: ConstraintLayout,
		rating: RatingBar
	) {
		val fireStore = FirebaseFirestore.getInstance().collection("users")
			.document(mFirestoreClass.currentUser()).collection("movies")
			.document(myMovieAllData.details.title)
		fireStore.update("status", statusList[position])
		if (position == 1) {
			container.setBackgroundColor(
				ContextCompat.getColor(
					requireContext(),
					R.color.tertiary_lite_color
				)
			)
		} else {
			container.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
			changingRating(myMovieAllData, 0.0f)
			rating.rating = 0f
		}
	}
	
}