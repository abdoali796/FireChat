package com.abdoali.firebasetest.dataLayer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.abdoali.firebasetest.MainActivity
import com.abdoali.firebasetest.R
import kotlin.random.Random

val CHANAN_DI = "NEW Message"
fun notification(context: Context , title: String? , message: String?) {
    val intent = Intent(context , MainActivity::class.java)
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationID = Random.nextInt()

    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(notificationManager)
    }
    val pendingIntent = PendingIntent.getActivity(context , 0 , intent , PendingIntent.FLAG_MUTABLE)
    val notification = NotificationCompat.Builder(context , CHANAN_DI)
        .setContentTitle(title)
        .setContentText(message)
        .setOnlyAlertOnce(true)
        .setSmallIcon(R.mipmap.logos_foreground)
        .setContentIntent(pendingIntent)
        .build()

    notificationManager.notify(notificationID , notification)


}

@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(notificationManager: NotificationManager) {
    val channelName = "channelName"
    val channel = NotificationChannel(CHANAN_DI , channelName , NotificationManager.IMPORTANCE_HIGH)
    notificationManager.createNotificationChannel(channel)
}