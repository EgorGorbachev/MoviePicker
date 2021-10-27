package com.example.movie_picker.view.ui.search_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_picker.data.rest.model.MovieForList
import com.example.movie_picker.databinding.PopularMovesItemBinding
import com.squareup.picasso.Picasso

class SearchAdapter(private val listener: OnItemClickListener, private val onReadMoreClick: OnReadMoreClick) :
	PagingDataAdapter<MovieForList, SearchAdapter.MovieViewHolder>(MOVIE_COMPARATOR){
	override fun onBindViewHolder(holder: SearchAdapter.MovieViewHolder, position: Int) {
		val currentItem = getItem(position)
		
		if (currentItem != null) {
			holder.bind(currentItem)
			
		}
	}
	
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): MovieViewHolder {
		val binding =
			PopularMovesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return MovieViewHolder(binding)
	}
	
	inner class MovieViewHolder(private val binding: PopularMovesItemBinding) :
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
		
		fun bind(movieForList: MovieForList) {
			binding.apply {
				popularMovieTitle.text = movieForList.title
				Picasso.get().load("https://image.tmdb.org/t/p/w500/${movieForList.poster_path}").into(popularMovieImage)
				val date = movieForList.release_date.split("-").reversed().joinToString(".")
				popularMovieRealiseDate.text = date
				popularMovieVote.text = movieForList.vote_average.toString()
				popularMovieOverview.text = movieForList.overview+"\n"
				popularMovieBtn.setOnClickListener {
					onReadMoreClick.onReadMoreClick()
				}
			}
			
		}
		
	}
	
	interface OnItemClickListener {
		fun onItemClick(movieForList: MovieForList)
	}
	
	companion object {
		private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieForList>() {
			override fun areItemsTheSame(oldItem: MovieForList, newItem: MovieForList) =
				oldItem.id == newItem.id
			
			override fun areContentsTheSame(
				oldItem: MovieForList,
				newItem: MovieForList
			) = oldItem == newItem
		}
		
	}
	
	interface OnReadMoreClick {
		fun onReadMoreClick()
	}
	
}