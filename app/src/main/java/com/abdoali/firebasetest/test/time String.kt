package com.abdoali.firebasetest.test

import java.text.SimpleDateFormat
import java.util.*


fun timeString(time:Long):String{
    val formatter = SimpleDateFormat("hh:mm" , Locale.getDefault())
    return  formatter.format(time)
}