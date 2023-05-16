package com.abdoali.firebasetest.dataLayer

sealed class LoginSata{
    class Error(val result: String) : LoginSata()
    object Loading:LoginSata()
    object Succeed:LoginSata()
}
