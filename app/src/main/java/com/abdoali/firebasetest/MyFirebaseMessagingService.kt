package com.abdoali.firebasetest

import android.util.Log
import com.abdoali.firebasetest.dataLayer.notification
import com.abdoali.firebasetest.login.TAGVM
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

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
        notification(context = this, title =message.data["title"], message = message.data["message"] )
//        val intent = Intent(this ,MainActivity::class.java)
//        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        val notificationID = Random.nextInt()
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//                createNotificationChannel(notificationManager)
//            }
//        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE)
//        val notification=NotificationCompat.Builder(this,CHANAN_DI)
//            .setContentTitle(message.data["title"])
//            .setContentText(message.data["message"])
//            .setSmallIcon(R.drawable.message)
//            .setContentIntent(pendingIntent)
//            .build()
//
//        notificationManager.notify(notificationID,notification)
//
//
//    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel(notificationManager: NotificationManager){
//        val channelName="channelName"
//        val channel= NotificationChannel(CHANAN_DI,channelName,NotificationManager.IMPORTANCE_HIGH)
//        notificationManager.createNotificationChannel(channel)
    }
}