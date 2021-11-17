package com.example.movie_picker.view.ui.my_movies_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_picker.R
import com.example.movie_picker.data.models.MyMovieAllData
import com.example.movie_picker.data.rest.model.MovieForList
import com.example.movie_picker.databinding.FragmentMyMoviesBinding
import com.example.movie_picker.di.AppInterfaceImpl
import com.example.movie_picker.firebase.FirestoreClass
import com.example.movie_picker.view.base.BaseFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MyMoviesFragment : BaseFragment(R.layout.fragment_my_movies),
	MyMoviesAdapter.OnItemClickListener, MyMoviesAdapter.OnRatingClickListener,
	MyMoviesAdapter.OnWatchingStatusClickListener, MyMoviesAdapter.OnReadMoreClickMyMovie{
	
	private val viewModel by viewModels<MyMoviesViewModel>()
	
	private var _binding: FragmentMyMoviesBinding? = null
	private val binding get() = _binding!!
	
	private lateinit var recyclerView: RecyclerView
	private val mFirestoreClass = FirestoreClass()
	
	private lateinit var spinnerAdapter: ArrayAdapter<String>
	
	private val statusList: MutableList<String> = mutableListOf("Not watched", "Watched")
	
	lateinit var adapter: MyMoviesAdapter
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentMyMoviesBinding.bind(view)
		
		MobileAds.initialize(requireContext())
		val adRequest = AdRequest.Builder().build()
		val adView = binding.adView
		adView.loadAd(adRequest)
		
		adapter = MyMoviesAdapter(this, this, this, this)
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
				.navigate(R.id.action_myMoviesFragment_to_settingsFragment)
		}
		
		binding.myMoviesBackBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_myMoviesFragment_to_searchFragment)
		}
	}
	
	private fun changingRating(myMovieAllData: MyMovieAllData, rating: Float) {
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
	
	override fun onReadMoreClickMyMovie(myMovieAllData: MyMovieAllData) {
		val movieDetails = MovieForList(
			myMovieAllData.details.id,
			myMovieAllData.details.original_language,
			myMovieAllData.details.original_title,
			myMovieAllData.details.title,
			myMovieAllData.details.overview,
			myMovieAllData.details.release_date,
			myMovieAllData.details.poster_path,
			myMovieAllData.details.vote_average
		)
		val action =
			MyMoviesFragmentDirections.actionMyMoviesFragmentToDetailsMovieFragment(movieDetails)
		findNavController().navigate(action)
	}
	
	
}