package com.example.colorgenerator.ui.components.methodbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.colorgenerator.models.ColorGenerationMethod

@ExperimentalMaterialApi
@Composable
fun MethodBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    backgroundColor: Color,
    selectedMethod: ColorGenerationMethod,
    onClick: (method: ColorGenerationMethod) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        modifier = Modifier.background(Color.Transparent),
        sheetPeekHeight = 0.dp,
        sheetElevation = 20.dp,
        sheetContent = {
            MethodList(
                backgroundColor = backgroundColor,
                methodList = ColorGenerationMethod.values(),
                selectedMethod = selectedMethod,
                onClick = onClick,
            )
        },
    ) {
        content(it)
    }
}