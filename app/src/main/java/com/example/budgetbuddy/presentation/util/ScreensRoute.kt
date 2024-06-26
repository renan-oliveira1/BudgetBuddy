package com.example.budgetbuddy.presentation.util

sealed class ScreensRoute(val route: String) {
    object HomeScreen: ScreensRoute("home_screen")
    object ProductScreen: ScreensRoute("product_screen")
    object ClientScreen: ScreensRoute("client_screen")
    object BudgetsScreen: ScreensRoute("budget_screen")
    object AddBudgetsScreen: ScreensRoute("add_budget_screen")
}