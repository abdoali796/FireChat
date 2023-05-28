package com.abdoali.firebasetest.dataLayer

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {
    @Headers("Authorization: key=${ConstrinFCM.SERVER_KEY}","Content_Type:${ConstrinFCM.CONTENT_TYPE}")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification:PushNotification
    ): Response<ResponseBody>



}

