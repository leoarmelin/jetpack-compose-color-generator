package com.example.colorgenerator.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.example.colorgenerator.extensions.getColorName
import com.example.colorgenerator.models.ColorGenerationMethod
import com.example.colorgenerator.models.navigation.MainNavRoutes
import com.example.colorgenerator.ui.components.ColorSquare
import com.example.colorgenerator.ui.components.methodbottomsheet.MethodBottomSheet
import com.example.colorgenerator.viewmodel.ColorViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ColorGeneratorScreen(
    colorViewModel: ColorViewModel,
    currentRoute: String,
    scaffoldState: ScaffoldState,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
) {
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    val onUpdateColor: (i: Int) -> Unit = { i ->
        colorViewModel.updateColorGeneratorValue(i)
    }

    val onLockColor: (i: Int) -> Unit = { i ->
        colorViewModel.updateColorGeneratorLock(i)
    }

    val onCopyColor: (i: Int) -> Unit = { i ->
        clipboardManager.setText(AnnotatedString(colorViewModel.colorGeneratorList[i].value.getColorName()))
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar("Copied: ${colorViewModel.colorGeneratorList[i].value.getColorName()}")
        }
    }

    val onSelectMethod: (method: ColorGenerationMethod) -> Unit = { method ->
        scope.launch {
            colorViewModel.generationMethod = method
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    MethodBottomSheet(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        backgroundColor = when (currentRoute) {
            MainNavRoutes.ColorGenerator.routeName -> Color(colorViewModel.colorGeneratorList.last().value)
            MainNavRoutes.GradientGenerator.routeName -> Color(colorViewModel.gradientGeneratorList.last().value)
            else -> Color(0)
        },
        selectedMethod = colorViewModel.generationMethod,
        onClick = onSelectMethod
    ) {
        Column {
            for (i in 0 until colorViewModel.colorGeneratorList.size) {
                ColorSquare(
                    Modifier.weight(1f),
                    colorViewModel.colorGeneratorList[i],
                    { onUpdateColor(i) },
                    { onLockColor(i) },
                    { onCopyColor(i) },
                    colorViewModel.generationMethod != ColorGenerationMethod.Random && i == 0
                )
            }
        }
    }
}