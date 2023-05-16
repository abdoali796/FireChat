package com.abdoali.firebasetest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.abdoali.firebasetest.chat.chats
import com.abdoali.firebasetest.login.LOGIN_SCREEN
import com.abdoali.firebasetest.login.info
import com.abdoali.firebasetest.login.login
import com.abdoali.firebasetest.mainScreen.Main_SCREEN
import com.abdoali.firebasetest.mainScreen.mainScreen
import com.abdoali.firebasetest.search.search
import com.abdoali.firebasetest.ui.theme.FirebaseTestTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()
    }

    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window,false)
        mySharedPreferences.initShared(applicationContext)
        val firebaseUser = firebaseAuth.currentUser

        setContent {

            FirebaseTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize() , color = MaterialTheme.colors.background
                ) {


                    val navController = rememberAnimatedNavController()
                    val isLogin = if (firebaseUser == null) LOGIN_SCREEN else Main_SCREEN
                    AnimatedNavHost(navController = navController , startDestination = isLogin) {
                        login(navController)
                        mainScreen(navController)
                        info(navController)
                        search(navController)
                        chats()
                    }

                }
            }
        }
    }
}


