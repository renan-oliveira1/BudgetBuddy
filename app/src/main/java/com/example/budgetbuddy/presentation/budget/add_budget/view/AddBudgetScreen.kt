package com.example.budgetbuddy.presentation.budget.add_budget.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgetbuddy.R
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.presentation.budget.add_budget.view.components.BudgetClientItem
import com.example.budgetbuddy.presentation.budget.add_budget.view.components.BudgetProductItem
import com.example.budgetbuddy.presentation.budget.add_budget.view_model.AddBudgetScreenViewModel
import com.example.budgetbuddy.presentation.util.ScreensRoute
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetScreen(
    navController: NavController,
    addBudgetScreenViewModel: AddBudgetScreenViewModel = hiltViewModel()
) {
    val scaffoldState = remember{ SnackbarHostState() }
    val stateClients = addBudgetScreenViewModel.stateClients.value
    val stateProducts = addBudgetScreenViewModel.stateProducts.value
    val stateTotalValue = addBudgetScreenViewModel.stateTotalValue.value

    var budgetName by remember { mutableStateOf("") }

    var selectedClient = addBudgetScreenViewModel.stateClientSelected.value
    var selectedTypeScreen by remember { mutableStateOf(false) }
    var showDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true){
        addBudgetScreenViewModel.showMessageSnackBar.collectLatest { event ->
            scaffoldState.showSnackbar(message = event.message)
        }
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                    ) {
                        Row (modifier = Modifier.fillMaxWidth()){
                            OutlinedButton(
                                onClick = {selectedTypeScreen = false},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                shape = RoundedCornerShape(0.dp),
                                border = BorderStroke(1.dp, Color.Blue),
                                colors = when(selectedTypeScreen){
                                    true -> ButtonDefaults.outlinedButtonColors(
                                        containerColor = MaterialTheme.colorScheme.surface,
                                        contentColor = MaterialTheme.colorScheme.primary,
                                    )
                                    false -> ButtonDefaults.outlinedButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.surface,
                                    )
                                }
                            ) {
                                Text("Clientes")
                            }
                            OutlinedButton(
                                onClick = {selectedTypeScreen = true},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                shape = RoundedCornerShape(0.dp),
                                colors = when(selectedTypeScreen){
                                    true -> ButtonDefaults.outlinedButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.surface,
                                    )
                                    false -> ButtonDefaults.outlinedButtonColors(
                                        containerColor = MaterialTheme.colorScheme.surface,
                                        contentColor = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            ) {
                                Text("Produtos")
                            }
                        }
                    }
                }

                LazyColumn {
                    if(!selectedTypeScreen){
                        items(stateClients){client ->
                            BudgetClientItem(client = client, modifier = Modifier.clickable { addBudgetScreenViewModel.setSelectedClient(client)}, isSelected = selectedClient == client)
                        }
                    }else{
                        items(stateProducts){product ->
                            BudgetProductItem(product, onClick = { addBudgetScreenViewModel.calculateTotalValue() })
                        }
                    }
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            budgetName = ""
                            showDialog = false
                        },
                        title = {
                            Text(text = "Nome do Orçamento")
                        },
                        text = {
                            Column {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    TextField(
                                        value = budgetName,
                                        onValueChange = { budgetName = it },
                                        label = { Text("Nome Orçamento") },
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .background(Color.Transparent),
                                        textStyle = TextStyle(color = Color.Black),
                                        shape = RoundedCornerShape(30.dp),
                                        colors = TextFieldDefaults.textFieldColors(
                                            disabledTextColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent
                                        )
                                    )
                                }
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    addBudgetScreenViewModel.save(budgetName, selectedClient, stateProducts)
                                    navController.navigate(ScreensRoute.BudgetsScreen.route)
                                    showDialog = false
                                }
                            ) {
                                Text("Confirmar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    budgetName = ""
                                    showDialog = false
                                }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.baseline_save_24), contentDescription = "Save budget")
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = "Total orçamento: ${String.format("%.2f", stateTotalValue)}",
                    modifier = Modifier.padding(10.dp),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}