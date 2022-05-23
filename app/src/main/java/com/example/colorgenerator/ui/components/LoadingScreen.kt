package com.example.colorgenerator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.colorgenerator.R

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF257683))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_brush),
            contentDescription = "Brush icon",
            modifier = Modifier.size(64.dp).align(Alignment.Center),
            tint = Color.White
        )
    }
}