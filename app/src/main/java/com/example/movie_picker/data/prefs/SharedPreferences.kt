package com.example.movie_picker.data.prefs

import android.content.Context

class SharedPreferences(context: Context) {
	
	private val sharPref = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
	
	fun setPref(key:String, value: String) {
		val editor = sharPref.edit()
		editor.putString(key,value)
		editor.apply()
	}
	
	fun getPrefStr(key:String): String? {
		return sharPref.getString(key,null)
	}
	
	fun setPref(key:String, value: Boolean) {
		val editor = sharPref.edit()
		editor.putBoolean(key,value)
		editor.apply()
	}
	
	fun getPrefBool(key:String): Boolean? {
		return sharPref.getBoolean(key,false)
	}
}