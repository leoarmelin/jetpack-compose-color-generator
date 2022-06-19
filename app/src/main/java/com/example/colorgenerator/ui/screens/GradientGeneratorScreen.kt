package com.example.colorgenerator.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.colorgenerator.viewmodel.ColorViewModel
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun GradientGeneratorScreen(colorViewModel: ColorViewModel, scaffoldState: ScaffoldState) {
    val animatedColorOne by animateColorAsState(Color(colorViewModel.gradientGeneratorList[0].value))
    val animatedColorTwo by animateColorAsState(Color(colorViewModel.gradientGeneratorList[1].value))

    var rotation by remember { mutableStateOf(-45f) }
    val state = rememberTransformableState { _, _, rotationChange ->
        rotation -= rotationChange
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state)
            .gradientBackground(listOf(animatedColorOne, animatedColorTwo), rotation)
    )
}

private fun Modifier.gradientBackground(colors: List<Color>, angle: Float) = this.then(
    Modifier.drawBehind {
        // Setting the angle in radians
        val angleRad = angle / 180f * PI

        // Fractional x
        val x = kotlin.math.cos(angleRad).toFloat()

        // Fractional y
        val y = kotlin.math.sin(angleRad).toFloat()

        // Set the Radius and offset as shown below
        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        // Setting the exact offset
        val exactOffset = Offset(
            x = kotlin.math.min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - kotlin.math.min(offset.y.coerceAtLeast(0f), size.height)
        )

        // Draw a rectangle with the above values
        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)