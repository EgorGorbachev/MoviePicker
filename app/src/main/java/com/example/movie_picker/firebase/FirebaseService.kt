package com.example.movie_picker.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.movie_picker.MainActivity
import com.example.movie_picker.R
import com.example.movie_picker.common.CHANNEL_ID
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

const val TAG = "MyApp"

class FirebaseService : FirebaseMessagingService() {
	
	override fun onMessageReceived(message: RemoteMessage) {
		super.onMessageReceived(message)
		
		FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
			if (!task.isSuccessful) {
				Log.w("lol", "Fetching FCM registration token failed", task.exception)
				return@OnCompleteListener
			}
			
			// Get new FCM registration token
			val token = task.result
			// Log and toast
			val msg = getString(R.string.msg_token_fmt, token)
			Log.d(TAG, msg)
			Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
		})
		
		val intent = Intent(this, MainActivity::class.java)
		val notificationManager =
			getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		val notificationID = Random.nextInt()
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			createNotificationChannel((notificationManager))
		}
		
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
		val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
		val notification = NotificationCompat.Builder(this, CHANNEL_ID)
			.setContentTitle(message.data["title"])
			.setContentText(message.data["message"])
			.setSmallIcon(R.drawable.ic_main_logo)
			.setAutoCancel(true)
			.setContentIntent(pendingIntent)
			.build()
		notificationManager.notify(notificationID, notification)
	}
	
	@RequiresApi(Build.VERSION_CODES.O)
	private fun createNotificationChannel(notificationManager: NotificationManager) {
		val channelName = "channelName"
		val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
			description = "My channel description"
			enableLights(true)
			lightColor = Color.BLUE
		}
		notificationManager.createNotificationChannel(channel)
	}
	
}