package com.example.colorgenerator.ui.components.methodbottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.colorgenerator.models.ColorGenerationMethod
import com.example.colorgenerator.ui.theme.montserratFontFamily

@Composable
fun MethodItem(
    isSelected: Boolean,
    method: ColorGenerationMethod,
    onClick: (method: ColorGenerationMethod) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onClick(method) }
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = method.name,
            fontFamily = montserratFontFamily,
            fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color.White,
        )
    }
}
