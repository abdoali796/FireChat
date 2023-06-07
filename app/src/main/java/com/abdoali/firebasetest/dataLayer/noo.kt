package com.abdoali.firebasetest.dataLayer

import android.util.Log
import com.abdoali.firebasetest.TAG_Token
import com.abdoali.firebasetest.mySharedPreferences
import com.google.firebase.messaging.FirebaseMessaging

fun topic(add: Boolean = true) {
    if (add) {

        FirebaseMessaging.getInstance().subscribeToTopic(mySharedPreferences.token.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i(TAG_Token , "subscribeToTopic(mySharedPreferences.")
                } else{
                    Log.i(TAG_Token , "ffffffffffffffharedPreferences.")
                }


            }
    }}