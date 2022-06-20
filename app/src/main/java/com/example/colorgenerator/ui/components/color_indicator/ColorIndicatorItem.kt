package com.example.colorgenerator.ui.components.color_indicator

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.example.colorgenerator.R
import com.example.colorgenerator.extensions.getColorName
import com.example.colorgenerator.models.ColorLock

@Composable
fun ColorIndicatorItem(
    color: ColorLock,
    onLongPress: () -> Unit,
    onTextClick: () -> Unit,
) {
    val density = LocalDensity.current

    val currentColor = Color(color.value)
    val hasContrastWithWhite =
        ColorUtils.calculateContrast(currentColor.toArgb(), Color.White.toArgb()) <= 1.5f
    val animatedColor = animateColorAsState(currentColor)

    var isTextVisible by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            visible = isTextVisible,
            enter = fadeIn() + slideInHorizontally {
                with(density) { 40.dp.roundToPx() }
            },
            exit = fadeOut() + slideOutHorizontally {
                with(density) { 40.dp.roundToPx() }
            }
        ) {
            Text(
                text = color.value.getColorName(),
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable { onTextClick() }
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(animatedColor.value, RoundedCornerShape(8.dp))
                .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { isTextVisible = !isTextVisible },
                        onLongPress = { onLongPress() }
                    )
                }
        ) {
            if (color.isLocked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "Lock icon",
                    tint = if (hasContrastWithWhite) Color(0xC00D0D0D) else Color(0xC0FDFDFD),
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}