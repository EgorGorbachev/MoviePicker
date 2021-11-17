package com.example.movie_picker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.movie_picker.common.TOPIC
import com.example.movie_picker.data.models.FirebaseNotification.NotificationsData
import com.example.movie_picker.data.models.FirebaseNotification.PushNotificationsData
import com.example.movie_picker.firebase.Notifications.NotificationsInstance
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	
	val TAG = "MainActivity"
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
		
		PushNotificationsData(NotificationsData("Hello, it`s movie picker!", "Let`s go!"), TOPIC).also {
			sendNotification(it)
		}
	}
	
	private fun sendNotification(notification: PushNotificationsData) =
		CoroutineScope(Dispatchers.IO).launch {
			try {
				val response = NotificationsInstance.notificationApi.postNotification(notification)
				if (response.isSuccessful) {
					Log.d(TAG, "Response: $response")
				} else {
					Log.e(TAG, response.errorBody().toString())
				}
			} catch (e: Exception) {
				Log.e(TAG, e.toString())
			}
		}
}