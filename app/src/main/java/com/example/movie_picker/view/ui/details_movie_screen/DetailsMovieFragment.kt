package com.example.movie_picker.view.ui.details_movie_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.movie_picker.R
import com.example.movie_picker.data.rest.model.MovieForList
import com.example.movie_picker.databinding.FragmentDetailsMovieBinding
import com.example.movie_picker.firebase.FirestoreClass
import com.example.movie_picker.view.base.BaseFragment
import com.squareup.picasso.Picasso

class DetailsMovieFragment : BaseFragment(R.layout.fragment_details_movie) {
	
	private val args by navArgs<DetailsMovieFragmentArgs>()
	
	
	private var _binding: FragmentDetailsMovieBinding? = null
	private val binding get() = _binding!!
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentDetailsMovieBinding.bind(view)
		
		val details = args.movieDetails
		
		binding.apply {
			Picasso.get().load("https://image.tmdb.org/t/p/w500/${details.poster_path}")
				.into(detailsImage)
			detailsTitle.text = details.title
			detailsRateValue.text = details.vote_average.toString()
			detailsDateValue.text = details.release_date
			detailsOverview.text = details.overview
			detailsOriginalTitleValue.text = details.original_title
			detailsOriginalLanguageValue.text = details.original_language
			detailsMyMovieBtn.setOnClickListener {
				val mFirestoreClass = FirestoreClass()
				toast(mFirestoreClass.currentUser())
				mFirestoreClass.addCollection(mFirestoreClass.currentUser(),details.title, details)
			}
		}
	}
	
}