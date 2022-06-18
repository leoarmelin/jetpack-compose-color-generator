package com.example.colorgenerator.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.colorgenerator.R

var iterationsCount = 0

@Composable
fun MenuAnimator(isOpen: Boolean) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.anim_menu)
    )

    if (iterationsCount <= 2) iterationsCount++

    if (isOpen) {
        LottieAnimation(
            composition = composition,
            clipSpec = LottieClipSpec.Progress(0f, 0.5f),
            isPlaying = iterationsCount > 2,
            modifier = Modifier.size(32.dp)
        )
    } else {
        LottieAnimation(
            composition = composition,
            isPlaying = iterationsCount > 2,
            clipSpec = LottieClipSpec.Progress(0.5f, 1f),
            modifier = Modifier.size(32.dp)
        )
    }
}