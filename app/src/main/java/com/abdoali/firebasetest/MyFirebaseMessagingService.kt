package com.abdoali.firebasetest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.abdoali.firebasetest.login.TAGVM
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService()  {
val CHANAN_DI="NEW Message"
    override fun onNewToken(token: String) {
        Log.i(TAGVM,token)
        mySharedPreferences.token= token
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i(TAG_Token,"okyyy"+message.data.toString())
        val intent = Intent(this ,MainActivity::class.java)
        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                createNotificationChannel(notificationManager)
            }
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE)
        val notification=NotificationCompat.Builder(this,CHANAN_DI)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.message)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID,notification)


    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channelName="channelName"
        val channel= NotificationChannel(CHANAN_DI,channelName,NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }
}