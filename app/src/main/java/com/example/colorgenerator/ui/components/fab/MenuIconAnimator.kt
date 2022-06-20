package com.example.colorgenerator.ui.components.fab

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.colorgenerator.R

// Use when the animation is fluid and not bugging.
@Composable
fun MenuIconAnimator(isOpen: Boolean?) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_menu))

    when (isOpen) {
        true -> LottieAnimation(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(
                min = 0,
                max = (composition?.durationFrames?.toInt() ?: 0) / 2,
            ),
            modifier = Modifier.size(32.dp)
        )
        false -> LottieAnimation(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(
                min = (composition?.durationFrames?.toInt() ?: 0) / 2,
                max = (composition?.durationFrames?.toInt() ?: 0),
            ),
            modifier = Modifier.size(32.dp)
        )
        else -> LottieAnimation(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(
                min = 0,
                max = 0,
            ),
            modifier = Modifier.size(32.dp)
        )
    }
}