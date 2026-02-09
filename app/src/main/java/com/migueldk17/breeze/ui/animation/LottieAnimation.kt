package com.migueldk17.breeze.ui.animation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants

@Composable
fun LottieAnimation(
    animationRes: Int,
    isPlaying: Boolean,
    size: Dp? = null,
    modifier: Modifier = Modifier,
    iterations: Int = LottieConstants.IterateForever
){
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animationRes))
    val animationSize = if (size != null) Modifier.size(size) else Modifier
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = iterations
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = animationSize
        )
    }
}