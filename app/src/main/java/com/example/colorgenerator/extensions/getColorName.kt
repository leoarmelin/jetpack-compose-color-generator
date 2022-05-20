package com.example.colorgenerator.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

fun Int.getColorName(): String {
    val color = Color(this).toArgb()

    return "#${color.red.toString(16).zeroBeforeNumeral().uppercase()}${
        color.green.toString(16).zeroBeforeNumeral().uppercase()
    }${color.blue.toString(16).zeroBeforeNumeral().uppercase()}"
}