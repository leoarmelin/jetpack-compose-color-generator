package com.example.colorgenerator.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.example.colorgenerator.R

@Composable
fun ColorSquare(
    modifier: Modifier,
    color: Int,
    colorString: String,
    isColorLocked: Boolean,
    onTap: () -> Unit,
    onLongPress: () -> Unit,
    onBoxClick: () -> Unit,
) {
    val currentColor = Color(color)
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
        Row(
            modifier = Modifier
                .padding(0.dp, 8.dp, 8.dp, 0.dp)
                .border(
                    width = 1.dp,
                    color = if (hasContrastWithWhite) Color(0xC00D0D0D) else Color(0xC0FDFDFD),
                    shape = RoundedCornerShape(8.dp),
                )
                .background(
                    if (hasContrastWithWhite) Color(0x400D0D0D) else Color(0x40FDFDFD),
                    shape = RoundedCornerShape(8.dp)
                )
                .align(Alignment.TopEnd)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onBoxClick() }
                    )
                }
        ) {
            if (isColorLocked) {
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
                text = colorString,
                fontSize = 18.sp,
                color = if (hasContrastWithWhite) Color(0xC00D0D0D) else Color(0xC0FDFDFD)
            )
        }
    }
}