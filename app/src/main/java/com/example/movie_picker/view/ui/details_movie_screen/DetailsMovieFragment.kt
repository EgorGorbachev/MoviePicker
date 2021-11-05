package com.example.movie_picker.view.ui.details_movie_screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.movie_picker.R
import com.example.movie_picker.data.models.MyMovieModel
import com.example.movie_picker.data.rest.model.MovieForList
import com.example.movie_picker.databinding.FragmentDetailsMovieBinding
import com.example.movie_picker.firebase.FirestoreClass
import com.example.movie_picker.view.base.BaseFragment
import com.google.rpc.context.AttributeContext
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsMovieFragment : BaseFragment(R.layout.fragment_details_movie) {
	
	private val args by navArgs<DetailsMovieFragmentArgs>()
	
	private val viewModel by viewModels<DetailsMoviesViewModel>()
	
	private var _binding: FragmentDetailsMovieBinding? = null
	private val binding get() = _binding!!
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentDetailsMovieBinding.bind(view)
		
		val details = args.movieDetails
		
		viewModel.myMoviesIdList()
		
		binding.apply {
			Picasso.get().load("https://image.tmdb.org/t/p/w500/${details.poster_path}")
				.into(detailsImage)
			detailsTitle.text = details.title
			detailsRateValue.text = details.vote_average.toString()
			detailsDateValue.text = details.release_date
			detailsOverview.text = details.overview
			detailsOriginalTitleValue.text = details.original_title
			detailsOriginalLanguageValue.text = details.original_language
			viewModel.list.observe(viewLifecycleOwner){
				if (it.contains(details.id.toString())) {
					detailsMyMovieBtn.isVisible = false
				} else {
					detailsMyMovieBtn.setOnClickListener {
						val mFirestoreClass = FirestoreClass()
						toast("Movie added to your list!")
						
						mFirestoreClass.addCollection(mFirestoreClass.currentUser(),details.title, details.id)
						
						viewModel.myMoviesIdList()
						
					}
				}
			}
			
		}
	}
	
}