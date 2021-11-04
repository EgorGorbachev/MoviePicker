package com.example.movie_picker.view.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.gorbachev_gmail.base.toast
import com.example.gorbachev_gmail.util.alertDialog

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
	
	fun Fragment.hideKeyboard() {
		view?.let { activity?.hideKeyboard(it) }
	}
	
	private fun Context.hideKeyboard(view: View) {
		val inputMethodManager =
			getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
	}
	
	fun toast(mes: String) {
		requireContext().toast(mes)
	}
	
	fun alertDialog(
		title: String,
		mes: String,
		positiveBtn: String,
		neutralBtn: String = "",
		NegativeBtn: String = "",
		countBtn: Int = 1,
	) {
		requireContext().alertDialog(
			title,
			mes,
			positiveBtn,
			neutralBtn,
			NegativeBtn,
			countBtn,
			requireContext()
		)
	}
	
	fun minimizeApp() {
		val startMain = Intent(Intent.ACTION_MAIN)
		startMain.addCategory(Intent.CATEGORY_HOME)
		startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
		startActivity(startMain)
	}
	
	companion object {
		const val TAG = "MyApp"
	}
	
}