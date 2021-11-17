package com.example.movie_picker.firebase.Notifications

import com.example.movie_picker.common.CONTENT_TYPE
import com.example.movie_picker.common.NOTIFICATION_BASE_URL
import com.example.movie_picker.common.SERVER_KEY
import com.example.movie_picker.data.models.FirebaseNotification.PushNotificationsData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationsApi {
	
	@Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
	@POST("fcm/send")
	suspend fun postNotification(
		@Body notification: PushNotificationsData
	): Response<ResponseBody>

}

class NotificationsInstance {
	
	companion object {
		private val notificationRetrofit by lazy {
			Retrofit.Builder()
				.baseUrl(NOTIFICATION_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
		}
		
		val notificationApi by lazy {
			notificationRetrofit.create(NotificationsApi::class.java)
		}
	}
	
}