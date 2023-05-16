package com.abdoali.firebasetest.dataLayer

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Mass(var uid: String = "d" , var time: Long = 0L , var text: String = "") {

}