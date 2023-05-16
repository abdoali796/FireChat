package com.abdoali.firebasetest.chat

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType

import androidx.navigation.navArgument
import com.abdoali.firebasetest.login.INFO
import com.abdoali.firebasetest.mainScreen.Main_SCREEN
import com.abdoali.firebasetest.search.SEARCH
import com.google.accompanist.navigation.animation.composable


const val CHATS = "chatsss"
private const val UID = "uidd"


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.chats() {
    composable(
        "$CHATS/{$UID}" ,
        enterTransition = {
            when (initialState.destination.route) {
                SEARCH -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                CHATS -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                INFO -> slideIntoContainer(
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
                    animationSpec = tween(350)
                )

                INFO -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                else -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(350)
                )
            }
        } , popEnterTransition = {
            when (initialState.destination.route) {
                SEARCH -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                CHATS -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                INFO -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                else -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(350)
                )
            }
        } , popExitTransition = {
            when (initialState.destination.route) {
                CHATS -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                INFO -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                else -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(350)
                )
            }
        } ,
        arguments = listOf(
            navArgument(UID) { NavType.StringType } ,

            )
    ) {
        Chats()
    }
}

fun NavController.navToChats(uid: String) {
    navigate("$CHATS/$uid") {
        popUpTo(Main_SCREEN) {

            inclusive = false
            saveState = false
        }




        launchSingleTop = true
    }
}

fun getUserChat(savedStateHandle: SavedStateHandle): String? {


    return savedStateHandle[UID]
}

