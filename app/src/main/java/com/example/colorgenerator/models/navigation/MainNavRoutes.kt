package com.example.colorgenerator.models.navigation

enum class MainNavRoutes(val routeName: String, val menuName: String) {
    Loading("loading", ""),
    ColorGenerator("random-generator", "Colors"),
    GradientGenerator("gradient-generator", "Gradients")
}