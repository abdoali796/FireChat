package com.abdoali.firebasetest

import android.content.Context
import android.content.SharedPreferences


object mySharedPreferences {
    private var myShared: SharedPreferences? = null
    private const val SHARED_NAME = "mySharedPreferences"
    private const val USER_NAME = "USERNAME"

    fun initShared(context: Context) {
        myShared = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    }

    var userName: String?
        get() = myShared?.getString(USER_NAME, null)
        set(value) {
            myShared?.edit()?.putString(USER_NAME, value)?.apply()
        }
}