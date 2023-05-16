package com.abdoali.firebasetest.mainScreen


import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.abdoali.firebasetest.login.INFO
import com.abdoali.firebasetest.login.LOGIN_SCREEN
import com.abdoali.firebasetest.login.TAGVM
import com.abdoali.firebasetest.search.SEARCH
import com.google.accompanist.navigation.animation.composable

const val Main_SCREEN = "TESTS_SCREEN"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainScreen(navController: NavController) {
    composable(Main_SCREEN ,
        enterTransition = {
            when (initialState.destination.route) {
                SEARCH -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Down ,
                    animationSpec = tween(350)
                )

                INFO -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                else ->  slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )
            }
        } ,
        exitTransition = {
            when (targetState.destination.route) {
                SEARCH -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Up ,
                    animationSpec = tween(350)
                )


                INFO -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(350)
                )

                else ->slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350))

            }
        } , popEnterTransition = {
            Log.i(TAGVM ,initialState.destination.route.toString())
            when (initialState.destination.route) {
                SEARCH -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Down ,
                    animationSpec = tween(350)
                )



                INFO -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

                else -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Start ,
                    animationSpec = tween(350))
            }
        } , popExitTransition = {
            when (initialState.destination.route) {
                SEARCH -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.End ,
                    animationSpec = tween(350)
                )

//                CHATS -> slideOutOfContainer(
//                    AnimatedContentScope.SlideDirection.End ,
//                    animationSpec = tween(350)
//                )
////
//                INFO -> slideOutOfContainer(
//                    AnimatedContentScope.SlideDirection.End ,
//                    animationSpec = tween(350)
//                )

                else -> null
            }
        }
    ) {
        MainScreen(navController = navController)

    }

}

fun NavController.popupToMain() {
    navigate(Main_SCREEN) {
        popUpTo(this@popupToMain.graph.id) {
            inclusive = true
            clearBackStack(LOGIN_SCREEN)
        }

    }
}