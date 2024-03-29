package com.example.colorgenerator.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.example.colorgenerator.extensions.getColorName
import com.example.colorgenerator.ui.components.ColorSquare
import com.example.colorgenerator.viewmodel.ColorViewModel

@Composable
fun ColorGeneratorScreen(
    colorViewModel: ColorViewModel,
) {
    val clipboardManager = LocalClipboardManager.current

    Column {
        for (i in 0 until colorViewModel.colorGeneratorList.size) {
            ColorSquare(
                Modifier.weight(1f),
                colorViewModel.colorGeneratorList[i].value,
                colorViewModel.colorGeneratorList[i].value.getColorName(),
                colorViewModel.colorGeneratorList[i].isLocked,
                { colorViewModel.updateColorGeneratorValue(i) },
                { colorViewModel.updateColorGeneratorLock(i) },
                {
                    clipboardManager.setText(AnnotatedString(colorViewModel.colorGeneratorList[i].value.getColorName()))
                }
            )
        }
    }
}