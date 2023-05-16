package com.abdoali.firebasetest.search


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.abdoali.firebasetest.chat.CHATS
import com.abdoali.firebasetest.mainScreen.Main_SCREEN
import com.google.accompanist.navigation.animation.composable

const val SEARCH = "SEARCH"


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.search(navController: NavController) {
    composable(SEARCH ,
        enterTransition = {
            when (initialState.destination.route) {
                Main_SCREEN -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up ,
                    animationSpec = tween(350)
                )
//
                else -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up ,
                    animationSpec = tween(350)
                )
            }
        } ,
        exitTransition = {
            when (targetState.destination.route) {
                CHATS -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(350)
                )

                else -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )
            }
        } , popEnterTransition = {
            when (initialState.destination.route) {
//            SEARCH->    slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween350))
//
//            CHATS ->   slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween350))
//            INFO -> slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween350))
                else -> null
            }
        } , popExitTransition = {
            when (targetState.destination.route) {
                CHATS -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(350)
                )
//            INFO -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween350))
                else -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down ,
                    animationSpec = tween(350)
                )
            }
        }) {
        SearchUi(navController = navController)

    }

}


fun NavController.navToSearch() {
    navigate(SEARCH) {

        launchSingleTop = true

    }

}