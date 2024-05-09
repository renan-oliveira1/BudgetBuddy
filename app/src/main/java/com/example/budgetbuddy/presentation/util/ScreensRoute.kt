package com.example.budgetbuddy.presentation.util

sealed class ScreensRoute(val route: String) {
    object HomeScreen: ScreensRoute("home_screen")
    object ProductScreen: ScreensRoute("product_screen")
}