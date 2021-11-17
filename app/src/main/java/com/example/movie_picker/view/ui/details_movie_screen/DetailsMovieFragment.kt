package com.example.movie_picker.view.ui.details_movie_screen

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
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
		
		val mFirestoreClass = FirestoreClass()
		
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
				val idList = mutableListOf<Int>()
				for (item in it){
					idList.add(item["id"]!!.toInt())
				}
				if (idList.contains(details.id)) {
					detailsMyMovieBtn.isVisible = false
					detailsMyMovieDeleteBtn.isVisible = true
				} else {
					detailsMyMovieBtn.setOnClickListener {
						toast("Movie added to your list!")
						val movieIdMap:MutableMap<String, Int> = mutableMapOf("id" to  details.id, "rating" to 0)
						mFirestoreClass.addCollection("movies", details.title, movieIdMap)
						viewModel.myMoviesIdList()
					}
					Log.d("lol",it.toString())
				}
			}
			detailsMyMovieDeleteBtn.setOnClickListener {
				mFirestoreClass.deleteItem(details.title)
				toast("Movie deleted from your list!")
				detailsMyMovieBtn.isVisible = true
				detailsMyMovieDeleteBtn.isVisible = false
				Navigation.findNavController(requireView()).navigate(R.id.action_detailsMovieFragment_to_searchFragment)
			}
			
		}
	}
	
}