package com.example.colorgenerator.ui.components.fab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuItem(itemName: String, iconId: Int? = null, onClick: (itemName: String) -> Unit) {
    Row(
        modifier = Modifier
            .background(Color(0x80000000), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(itemName) }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (iconId != null) {
            val icon = rememberVectorPainter(ImageVector.vectorResource(iconId))

            Icon(
                painter = icon,
                contentDescription = "Menu icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(16.dp),
                tint = Color.White
            )
        }

        Text(
            text = itemName,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}