package com.example.colorgenerator.ui.components.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.colorgenerator.R

@Composable
fun AnimatedFAB(
    isOpen: Boolean?,
    onMenuClick: () -> Unit,
    onSelectItem: (itemName: String) -> Unit
) {
    val menuIcon = rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_menu))
    val closeIcon = rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_close))

    Box {
        FloatingActionButton(
            onClick = onMenuClick,
            modifier = Modifier.align(Alignment.BottomEnd),
            backgroundColor = Color(0xFF257683)
        ) {
            AnimatedVisibility(visible = isOpen != true, enter = fadeIn(initialAlpha = -3f), exit = fadeOut()) {
                Icon(
                    painter = menuIcon,
                    contentDescription = "Menu icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
            AnimatedVisibility(visible = isOpen == true, enter = fadeIn(initialAlpha = -3f), exit = fadeOut()) {
                Icon(
                    painter = closeIcon,
                    contentDescription = "Close icon",
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )
            }
        }

        MenuList(isOpen, onSelectItem)
    }
}