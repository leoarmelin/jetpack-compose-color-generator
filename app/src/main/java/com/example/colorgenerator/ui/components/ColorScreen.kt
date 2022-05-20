package com.example.colorgenerator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.example.colorgenerator.extensions.getColorName
import com.example.colorgenerator.viewmodel.ColorViewModel
import kotlinx.coroutines.launch

@Composable
fun ColorScreen(colorViewModel: ColorViewModel) {
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState
    ) {
        Column {
            ColorSquare(
                Modifier.weight(1f),
                colorViewModel.colorOne.value,
                colorViewModel.colorOne.value.getColorName(),
                colorViewModel.colorOne.isLocked,
                { colorViewModel.updateColorOneValue() },
                {
                    colorViewModel.updateColorOneLock()
                },
                {
                    clipboardManager.setText(AnnotatedString(colorViewModel.colorOne.value.getColorName()))
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Copied: ${colorViewModel.colorOne.value.getColorName()}")
                    }
                }
            )

            ColorSquare(
                Modifier.weight(1.1f),
                colorViewModel.colorTwo.value,
                colorViewModel.colorTwo.value.getColorName(),
                colorViewModel.colorTwo.isLocked,
                { colorViewModel.updateColorTwoValue() },
                {
                    colorViewModel.updateColorTwoLock()
                },
                {
                    clipboardManager.setText(AnnotatedString(colorViewModel.colorTwo.value.getColorName()))
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Copied: ${colorViewModel.colorTwo.value.getColorName()}")
                    }
                }
            )

            ColorSquare(
                Modifier.weight(1.1f),
                colorViewModel.colorThree.value,
                colorViewModel.colorThree.value.getColorName(),
                colorViewModel.colorThree.isLocked,
                { colorViewModel.updateColorThreeValue() },
                {
                    colorViewModel.updateColorThreeLock()
                },
                {
                    clipboardManager.setText(AnnotatedString(colorViewModel.colorThree.value.getColorName()))
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Copied: ${colorViewModel.colorThree.value.getColorName()}")
                    }
                }
            )

            ColorSquare(
                Modifier.weight(1.1f),
                colorViewModel.colorFour.value,
                colorViewModel.colorFour.value.getColorName(),
                colorViewModel.colorFour.isLocked,
                { colorViewModel.updateColorFourValue() },
                {
                    colorViewModel.updateColorFourLock()
                },
                {
                    clipboardManager.setText(AnnotatedString(colorViewModel.colorFour.value.getColorName()))
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Copied: ${colorViewModel.colorFour.value.getColorName()}")
                    }
                }
            )

            ColorSquare(
                Modifier.weight(1f),
                colorViewModel.colorFive.value,
                colorViewModel.colorFive.value.getColorName(),
                colorViewModel.colorFive.isLocked,
                { colorViewModel.updateColorFiveValue() },
                {
                    colorViewModel.updateColorFiveLock()
                },
                {
                    clipboardManager.setText(AnnotatedString(colorViewModel.colorFive.value.getColorName()))
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Copied: ${colorViewModel.colorFive.value.getColorName()}")
                    }
                }
            )
        }
    }
}