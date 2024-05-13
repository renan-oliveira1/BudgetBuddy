package com.example.budgetbuddy

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetbuddy.presentation.budget.add_budget.view.AddBudgetScreen
import com.example.budgetbuddy.presentation.budget.budgets.view.BudgetsScreen
import com.example.budgetbuddy.presentation.client.view.ClientScreen
import com.example.budgetbuddy.presentation.home.HomeScreen
import com.example.budgetbuddy.presentation.products.view.ProductScreen
import com.example.budgetbuddy.presentation.util.ScreensRoute
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetBuddyTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreensRoute.HomeScreen.route
                    ) {
                         composable(
                             route = ScreensRoute.HomeScreen.route
                         ){
                            HomeScreen(navController = navController)
                         }
                        composable(
                            route = ScreensRoute.ProductScreen.route
                        ){
                            ProductScreen(navController)
                        }
                        composable(
                            route = ScreensRoute.ClientScreen.route
                        ){
                            ClientScreen(navController = navController)
                        }
                        composable(
                            route = ScreensRoute.BudgetsScreen.route
                        ){
                            BudgetsScreen(navController = navController)
                        }
                        composable(
                            route = ScreensRoute.AddBudgetsScreen.route
                        ){
                            AddBudgetScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}