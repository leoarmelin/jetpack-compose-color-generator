package com.example.colorgenerator.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.colorgenerator.models.navigation.MainNavRoutes

class NavigationViewModel : ViewModel() {
    var currentRoute by mutableStateOf(MainNavRoutes.Loading.routeName)

    fun setRoute(route: String) {
        currentRoute = route
    }
}