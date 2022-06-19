package com.example.colorgenerator.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.colorgenerator.models.navigation.MainNavRoutes
import com.example.colorgenerator.ui.screens.LoadingScreen
import com.example.colorgenerator.ui.screens.ColorGeneratorScreen
import com.example.colorgenerator.ui.screens.GradientGeneratorScreen
import com.example.colorgenerator.viewmodel.ColorViewModel
import com.example.colorgenerator.viewmodel.NavigationViewModel
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(
    modifier: Modifier,
    navigationViewModel: NavigationViewModel,
    colorViewModel: ColorViewModel,
    scaffoldState: ScaffoldState,
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as? Activity
    var closeCount by remember { mutableStateOf(0) }

    BackHandler {
        closeCount++
        if (closeCount == 2) {
            activity?.finish()
            return@BackHandler
        }
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar("Press again to close the app.")
            closeCount = 0
        }
    }

    Box(modifier = modifier) {
        NavHost(navController = navController, startDestination = MainNavRoutes.Loading.routeName) {
            composable(MainNavRoutes.Loading.routeName) {
                LoadingScreen()
            }
            composable(MainNavRoutes.ColorGenerator.routeName) {
                ColorGeneratorScreen(colorViewModel = colorViewModel, scaffoldState = scaffoldState)
            }
            composable(MainNavRoutes.GradientGenerator.routeName) {
                GradientGeneratorScreen(colorViewModel = colorViewModel, scaffoldState = scaffoldState)
            }
        }
    }

    navController.navigate(navigationViewModel.currentRoute)
}