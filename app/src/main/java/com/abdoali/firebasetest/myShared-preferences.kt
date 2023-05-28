package com.abdoali.firebasetest

import android.content.Context
import android.content.SharedPreferences


object mySharedPreferences {
    private var myShared: SharedPreferences? = null
    private const val SHARED_NAME = "mySharedPreferences"
    private const val TOKEN = "TOKEN_TOKEN"

    fun initShared(context: Context) {
        myShared = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    }

    var token: String?
        get() = myShared?.getString(TOKEN, null)
        set(value) {
            myShared?.edit()?.putString(TOKEN, value)?.apply()
        }
}
const val TAG_Token = "TOKEN_TOKEN"