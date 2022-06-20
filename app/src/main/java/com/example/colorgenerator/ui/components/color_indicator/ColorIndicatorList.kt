package com.example.colorgenerator.ui.components.color_indicator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.colorgenerator.models.ColorLock

@Composable
fun ColorIndicatorList(
    modifier: Modifier,
    colorList: List<ColorLock>,
    onLongPress: (i: Int) -> Unit,
    onTextClick: (i: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        itemsIndexed(colorList) { i, color ->
            ColorIndicatorItem(
                color,
                { onLongPress(i) },
                { onTextClick(i) }
            )
        }
    }
}