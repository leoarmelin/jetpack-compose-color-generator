package com.example.colorgenerator.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.colorgenerator.R
import com.example.colorgenerator.viewmodel.ColorViewModel

@Composable
fun ShareIcon(modifier: Modifier, viewModel: ColorViewModel, onShare: () -> Unit) {
    val currentColor = Color(viewModel.colorOne.value)
    val hasContrastWithWhite =
        ColorUtils.calculateContrast(currentColor.toArgb(), Color.White.toArgb()) <= 1.5f

    Icon(
        painter = painterResource(id = R.drawable.ic_share),
        contentDescription = "Share icon",
        tint = if (hasContrastWithWhite) Color(0xC00D0D0D) else Color(0xC0FDFDFD),
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onShare()
                    }
                )
            }
            .padding(16.dp)
            .size(24.dp)
    )
}