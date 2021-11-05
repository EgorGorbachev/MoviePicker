package com.example.movie_picker.view.ui.my_movies_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_picker.data.models.MyMovieModel
import com.example.movie_picker.databinding.MyMoviesResyclerItemBinding
import com.example.movie_picker.databinding.PopularMovesItemBinding
import com.squareup.picasso.Picasso

class MyMoviesAdapter(
	private val listener: OnItemClickListener
) : ListAdapter<MyMovieModel, MyMoviesAdapter.MyMovieViewHolder>(MY_MOVIE_COMPARATOR) {
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
		
		fun bind(myMovieModel: MyMovieModel) {
			binding.apply {
				Picasso.get().load("https://image.tmdb.org/t/p/w500/${myMovieModel.poster_path}").into(binding.myMovieImage)
				binding.myMovieTitle.text = myMovieModel.title
				binding.myMovieStatusValue.text = "Not Watched"
				binding.myMovieRating.rating = 4f
			}
		}
		
	}
	
	interface OnItemClickListener {
		fun onItemClick(myMovieModel: MyMovieModel)
	}
	
	companion object {
		private val MY_MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MyMovieModel>() {
			override fun areItemsTheSame(oldItem: MyMovieModel, newItem: MyMovieModel) =
				oldItem.id == newItem.id
			
			override fun areContentsTheSame(
				oldItem: MyMovieModel,
				newItem: MyMovieModel
			) = oldItem == newItem
		}
		
	}
}