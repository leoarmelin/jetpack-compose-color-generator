package com.example.colorgenerator.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.colorgenerator.models.navigation.MainNavRoutes
import com.example.colorgenerator.ui.screens.LoadingScreen
import com.example.colorgenerator.ui.screens.RandomGeneratorScreen
import com.example.colorgenerator.viewmodel.ColorViewModel
import com.example.colorgenerator.viewmodel.NavigationViewModel

@Composable
fun MainNavHost(
    modifier: Modifier,
    navigationViewModel: NavigationViewModel,
    colorViewModel: ColorViewModel,
    scaffoldState: ScaffoldState,
) {
    val navController = rememberNavController()

    Box(modifier = modifier) {
        NavHost(navController = navController, startDestination = MainNavRoutes.Loading.routeName) {
            composable(MainNavRoutes.Loading.routeName) {
                LoadingScreen()
            }
            composable(MainNavRoutes.RandomGenerator.routeName) {
                RandomGeneratorScreen(colorViewModel = colorViewModel, scaffoldState = scaffoldState)
            }
        }
    }

    navController.navigate(navigationViewModel.currentRoute)
}