package com.example.colorgenerator.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.example.colorgenerator.R
import com.example.colorgenerator.extensions.getColorName
import com.example.colorgenerator.models.ColorLock
import com.example.colorgenerator.ui.theme.montserratFontFamily

@Composable
fun ColorSquare(
    modifier: Modifier,
    color: ColorLock,
    onTap: () -> Unit,
    onLongPress: () -> Unit,
    onBoxClick: () -> Unit,
    isBaseColor: Boolean = false
) {
    val currentColor = Color(color.value)
    val hasContrastWithWhite =
        ColorUtils.calculateContrast(currentColor.toArgb(), Color.White.toArgb()) <= 1.5f
    val animatedColor = animateColorAsState(currentColor)

    Box(modifier = modifier
        .background(color = animatedColor.value)
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = { onLongPress() },
                onTap = { onTap() }
            )
        }
    ) {
        Column(modifier = Modifier
            .align(Alignment.Center)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onBoxClick() }
                )
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isBaseColor) {
                Text(
                    text = "BASE COLOR",
                    fontSize = 16.sp,
                    color = if (hasContrastWithWhite) Color(0xC00D0D0D) else Color(0xC0FDFDFD),
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
            }

            Row {
                if (color.isLocked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Lock icon",
                        tint = if (hasContrastWithWhite) Color(0xC00D0D0D) else Color(0xC0FDFDFD),
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 8.dp, 0.dp)
                            .size(18.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

                Text(
                    text = color.value.getColorName(),
                    fontSize = 20.sp,
                    color = if (hasContrastWithWhite) Color(0xC00D0D0D) else Color(0xC0FDFDFD),
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}