package com.abdoali.firebasetest.login

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.abdoali.firebasetest.Info
import com.abdoali.firebasetest.mainScreen.Main_SCREEN
import com.google.accompanist.navigation.animation.composable

const val INFO = "INfo"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.info(navController: NavController) {
    composable(INFO , enterTransition = {
        when (initialState.destination.route) {

            LOGIN_SCREEN -> slideIntoContainer(
                AnimatedContentScope.SlideDirection.End ,
                animationSpec = tween(350)
            )

            else -> slideIntoContainer(
                AnimatedContentScope.SlideDirection.Start ,
                animationSpec = tween(350)
            )
        }
    } ,
        exitTransition = {
            when (targetState.destination.route) {



                else -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )
            }
        } , popEnterTransition = {
            when (initialState.destination.route) {
//                SEARCH -> slideIntoContainer(
//                    AnimatedContentScope.SlideDirection.Left ,
//                    animationSpec = tween(350)
//                )
//
//                CHATS -> slideIntoContainer(
//                    AnimatedContentScope.SlideDirection.Right ,
//                    animationSpec = tween(350)
//                )
//
//                INFO -> slideIntoContainer(
//                    AnimatedContentScope.SlideDirection.Left ,
//                    animationSpec = tween(350)
//                )

                else -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(350)
                )
            }
        } , popExitTransition = {
            when (initialState.destination.route) {




                else -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )
            }
        }) {
        Info(navController)
    }
}

fun NavController.navToInfo() {
    navigate(INFO) {
        popUpTo(Main_SCREEN) {
            inclusive = false
        }
        launchSingleTop = true
    }
}