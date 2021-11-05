package com.example.movie_picker.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyMovieModel(
	val id:Int,
	val original_language: String,
	val original_title: String,
	val title: String,
	val overview: String,
	val release_date: String,
	val poster_path:String?,
	val vote_average: Double
): Parcelable