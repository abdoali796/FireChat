package com.abdoali.firebasetest.temp

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.abdoali.firebasetest.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun Loading(
    isLoading:(Boolean)-> Unit
) {
    val animation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.ty))

    val animationState by animateLottieCompositionAsState(
        composition = animation , iterations = Int.MAX_VALUE
    )
    Dialog(onDismissRequest = { isLoading(true) }) {
        LottieAnimation(composition = animation ,
            progress = { animationState } ,
            modifier = Modifier.size(300.dp))
    }
}

@Preview
@Composable
fun LoadingP() {
    Loading(isLoading = {})
}