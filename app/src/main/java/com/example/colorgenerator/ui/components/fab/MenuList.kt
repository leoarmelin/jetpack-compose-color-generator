package com.example.colorgenerator.ui.components.fab

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.colorgenerator.R
import com.example.colorgenerator.models.navigation.MainNavRoutes

@Composable
fun MenuList(isOpen: Boolean?, onSelectItem: (itemName: String) -> Unit) {
    val namesList = remember {
        listOf(
            MainNavRoutes.ColorGenerator,
            MainNavRoutes.GradientGenerator
        )
    }

    Column(
        modifier = Modifier
            .padding(bottom = 72.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        namesList.forEachIndexed { index, route ->
            AnimatedVisibility(
                visible = isOpen ?: false,
                enter = fadeIn(
                    initialAlpha = -1.5f * (namesList.size - index)
                ),
                exit = fadeOut(
                    targetAlpha = -1.5f * (namesList.size - index)
                )
            ) {
                MenuItem(route.menuName, null, onSelectItem)
            }
        }

        AnimatedVisibility(
            visible = isOpen ?: false,
            enter = fadeIn(
                initialAlpha = 0f
            ),
            exit = fadeOut(
                targetAlpha = 0f
            )
        ) {
            MenuItem("Share", R.drawable.ic_share, onSelectItem)
        }
    }
}