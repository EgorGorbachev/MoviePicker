package com.example.movie_picker.view.ui.my_movies_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_picker.data.models.MyMovieAllData
import com.example.movie_picker.data.models.MyMovieModel
import com.example.movie_picker.databinding.MyMoviesResyclerItemBinding
import com.example.movie_picker.databinding.PopularMovesItemBinding
import com.example.movie_picker.firebase.FirestoreClass
import com.squareup.picasso.Picasso

class MyMoviesAdapter(
	private val listener: OnItemClickListener,
	private val ratingListener: OnRatingClickListener
) : ListAdapter<MyMovieAllData, MyMoviesAdapter.MyMovieViewHolder>(MY_MOVIE_COMPARATOR) {
	override fun onBindViewHolder(holder: MyMoviesAdapter.MyMovieViewHolder, position: Int) {
		val currentItem = getItem(position)
		
		if (currentItem != null) {
			holder.bind(currentItem)
			
		}
	}
	
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): MyMoviesAdapter.MyMovieViewHolder {
		val binding =
			MyMoviesResyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return MyMovieViewHolder(binding)
	}
	
	inner class MyMovieViewHolder(private val binding: MyMoviesResyclerItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		
		init {
			binding.root.setOnClickListener {
				val position = bindingAdapterPosition
				if (position != RecyclerView.NO_POSITION) {
					val item = getItem(position)
					if (item != null) {
						listener.onItemClick(item)
					}
				}
				
			}
		}
		
		fun bind(myMovieAllData: MyMovieAllData) {
			binding.apply {
				Picasso.get().load("https://image.tmdb.org/t/p/w500/${myMovieAllData.details.poster_path}").into(binding.myMovieImage)
				binding.myMovieTitle.text = myMovieAllData.details.title
				binding.myMovieStatusValue.text = "Not Watched"
				binding.myMovieRating.rating = myMovieAllData.rating.toFloat()
				binding.myMovieRating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
					ratingListener.onRatingClick(myMovieAllData,rating)
				}
			}
		}
		
	}
	
	interface OnItemClickListener {
		fun onItemClick(myMovieAllData: MyMovieAllData)
	}
	
	interface OnRatingClickListener {
		fun onRatingClick(myMovieAllData: MyMovieAllData, rating:Float)
	}

	
	companion object {
		private val MY_MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MyMovieAllData>() {
			override fun areItemsTheSame(oldItem: MyMovieAllData, newItem: MyMovieAllData) =
				oldItem.details.id == newItem.details.id
			
			override fun areContentsTheSame(
				oldItem: MyMovieAllData,
				newItem: MyMovieAllData
			) = oldItem == newItem
		}
		
	}
}