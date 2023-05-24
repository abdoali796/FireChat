package com.abdoali.firebasetest.temp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
fun ApplyChangeDialog(
    isLoading: (Boolean) -> Unit
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


@Composable
fun LoadingAnimationDialog(
    isLoading: (Boolean) -> Unit
) {
    val animation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))

    val animationState by animateLottieCompositionAsState(
        composition = animation , iterations = Int.MAX_VALUE
    )
    Dialog(onDismissRequest = { isLoading(true) }) {
        LottieAnimation(composition = animation ,
            progress = { animationState } ,
            modifier = Modifier.size(300.dp))
    }
}

@Composable
fun LoadingAnimation() {
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))

    val state by animateLottieCompositionAsState(
        composition = lottieComposition , iterations = Int.MAX_VALUE
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxWidth()
    ) {

            LottieAnimation(composition = lottieComposition ,
                progress = { state } ,
                modifier = Modifier.size(300.dp))


    }
}


@Preview
@Composable
fun LoadingP() {
    ApplyChangeDialog(isLoading = {})
}