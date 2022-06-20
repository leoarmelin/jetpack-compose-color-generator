package com.example.colorgenerator.ui.components.methodbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.colorgenerator.models.ColorGenerationMethod

@Composable
fun MethodList(
    backgroundColor: Color,
    methodList: Array<ColorGenerationMethod>,
    selectedMethod: ColorGenerationMethod,
    onClick: (method: ColorGenerationMethod) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 16.dp),
    ) {
        Divider(
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .alpha(0.5f)
        )

        for (method in methodList) {
            MethodItem(
                isSelected = method == selectedMethod,
                method = method,
                onClick = onClick
            )

            Divider(
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .alpha(0.5f)
            )
        }
    }
}