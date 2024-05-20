package com.example.budgetbuddy.presentation.budget.budgets.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgetbuddy.R
import com.example.budgetbuddy.domain.use_case.util.BudgetOrder
import com.example.budgetbuddy.domain.use_case.util.ClientOrder
import com.example.budgetbuddy.domain.use_case.util.OrderType
import com.example.budgetbuddy.presentation.budget.budgets.view.components.BudgetItem
import com.example.budgetbuddy.presentation.budget.budgets.view_model.BudgetScreenViewModel
import com.example.budgetbuddy.presentation.client.view.components.ClientItem
import com.example.budgetbuddy.presentation.products.view.components.ProductOrderMenu
import com.example.budgetbuddy.presentation.util.ScreensRoute
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetsScreen(
    navController: NavController,
    budgetsScreenViewModel: BudgetScreenViewModel = hiltViewModel()
) {
    val scaffoldState = remember{ SnackbarHostState() }
    val stateItems = budgetsScreenViewModel.stateBudgets.value
    val statusVisibility = budgetsScreenViewModel.stateVisibility.value


    LaunchedEffect(key1 = true){
        budgetsScreenViewModel.showMessageSnackBar.collectLatest { event ->
            scaffoldState.showSnackbar(message = event.message)
        }
    }

    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState)
        },

        topBar = { TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.navigate(ScreensRoute.HomeScreen.route)}) {
                    Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.Black)
                }
            },
            title = {
                Text(text = "Orçamentos", color = Color.Black)
            },
            actions = {
                IconButton(onClick = { budgetsScreenViewModel.changeVisibility() }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_filter_list_alt_24), contentDescription = "Filter!", tint = Color.Black)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )

        )
        },

        content = {
                paddingValues ->
            Column(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())
            ) {
                AnimatedVisibility(
                    visible = statusVisibility,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    ProductOrderMenu(
                        listOf("Nome", "Data Cadastro", "Cliente"),
                        filterCLick = {
                            when(it){
                                is String -> {
                                    if (it.equals("Nome")){
                                        budgetsScreenViewModel.getBudgets(BudgetOrder.Name(stateItems.budgetOrder.orderType))
                                    }else if(it.equals("Data Cadastro")){
                                        budgetsScreenViewModel.getBudgets(BudgetOrder.Date(stateItems.budgetOrder.orderType))
                                    }else if(it.equals("Cliente")){
                                        budgetsScreenViewModel.getBudgets(BudgetOrder.Client(stateItems.budgetOrder.orderType))
                                    }else if(it.equals("Crescente")){
                                        budgetsScreenViewModel.getBudgets(stateItems.budgetOrder.copyOrderType(
                                            OrderType.Ascending))
                                    }else{
                                        budgetsScreenViewModel.getBudgets(stateItems.budgetOrder.copyOrderType(
                                            OrderType.Descending))
                                    }
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                LazyColumn {
                    items(stateItems.budgets){ item ->
                        BudgetItem(
                            budget = item,
                            modifier = Modifier.clickable {
                                navController.navigate(ScreensRoute.AddBudgetsScreen.route+ "?budgetId=${item.budget.budgetId}")
                            },
                            onDeleteClick = {
                                budgetsScreenViewModel.deleteBudget(item.budget)
                            }
                        )
                    }
                }
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ScreensRoute.AddBudgetsScreen.route) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add budget")
            }
        }
    )
}