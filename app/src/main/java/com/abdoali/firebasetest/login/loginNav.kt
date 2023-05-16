package com.abdoali.firebasetest.login

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.abdoali.firebasetest.chat.CHATS
import com.abdoali.firebasetest.mainScreen.Main_SCREEN
import com.google.accompanist.navigation.animation.composable

const val LOGIN_SCREEN = "LOGIN_SCREEN"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.login(navController: NavController) {
    composable(LOGIN_SCREEN ,
        enterTransition = {
        when (initialState.destination.route) {

            Main_SCREEN -> slideIntoContainer(
                AnimatedContentScope.SlideDirection.End ,
                animationSpec = tween(350)
            )

            else -> slideIntoContainer(
                AnimatedContentScope.SlideDirection.End ,
                animationSpec = tween(350)
            )
        }
    } ,
        exitTransition = {
            when (initialState.destination.route) {
                CHATS -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(250)
                )

                INFO -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(700)
                )

                else -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(700)
                )
            }
        } , popEnterTransition = {
            when (initialState.destination.route) {

                CHATS -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(700)
                )

                INFO -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(700)
                )

                else -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(700)
                )
            }
        } , popExitTransition = {
            when (initialState.destination.route) {
                CHATS -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(700)
                )

                INFO -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(700)
                )

                else -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(700)
                )
            }
        }) {
        Login(navController = navController)
    }
}

fun NavController.singOut() {
    navigate(LOGIN_SCREEN) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}