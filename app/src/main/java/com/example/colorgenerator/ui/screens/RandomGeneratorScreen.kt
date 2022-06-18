package com.example.colorgenerator.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.example.colorgenerator.extensions.getColorName
import com.example.colorgenerator.ui.components.ColorSquare
import com.example.colorgenerator.viewmodel.ColorViewModel
import kotlinx.coroutines.launch

@Composable
fun RandomGeneratorScreen(colorViewModel: ColorViewModel, scaffoldState: ScaffoldState) {
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    Column {
        for (i in 0 until colorViewModel.colorList.size) {
            ColorSquare(
                Modifier.weight(1f),
                colorViewModel.colorList[i].value,
                colorViewModel.colorList[i].value.getColorName(),
                colorViewModel.colorList[i].isLocked,
                { colorViewModel.updateColorValue(i) },
                { colorViewModel.updateColorLock(i) },
                {
                    clipboardManager.setText(AnnotatedString(colorViewModel.colorList[i].value.getColorName()))
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Copied: ${colorViewModel.colorList[i].value.getColorName()}")
                    }
                }
            )
        }
    }
}