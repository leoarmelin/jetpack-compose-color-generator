package com.example.colorgenerator.ui.components.fab

import androidx.compose.foundation.layout.Box
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedFAB(
    isOpen: Boolean?,
    onMenuClick: () -> Unit,
    onSelectItem: (itemName: String) -> Unit
) {
    Box {
        FloatingActionButton(
            onClick = onMenuClick,
            modifier = Modifier.align(Alignment.BottomEnd),
            backgroundColor = Color(0xFF257683)
        ) {
            MenuIconAnimator(isOpen)
        }

        MenuList(isOpen, onSelectItem)
    }
}