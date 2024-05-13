package com.example.budgetbuddy.presentation.budget.add_budget.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.presentation.budget.add_budget.view.components.BudgetClientItem
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
    val stateItems = addBudgetScreenViewModel.stateClients.value

    var budgetName by remember { mutableStateOf("") }

    var selectedClient by remember{ mutableStateOf<Client>(Client("", "", timestamp = System.currentTimeMillis())) }

    LaunchedEffect(key1 = true){
        addBudgetScreenViewModel.showMessageSnackBar.collectLatest { event ->
            scaffoldState.showSnackbar(message = event.message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ScreensRoute.HomeScreen.route)}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                title = {
                    Text(text = "Adicionar Orçamento")
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
                    .fillMaxSize()
            ) {
                Row {
                    TextField(
                        value = budgetName,
                        onValueChange = { budgetName = it },
                        label = { Text("Nome do orçamento") },
                        modifier = Modifier
                            .padding(5.dp)
                            .background(Color.Transparent),
                        textStyle = TextStyle(color = Color.Black),
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                }
                if(selectedClient.name != ""){
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Cliente Selecionado:")
                        Text(text = "Nome: ${selectedClient.name}")
                        Text(text = "Cpf: ${selectedClient.cpf}")
                    }
                }
                LazyColumn {
                    items(stateItems){client ->
                        BudgetClientItem(client = client, modifier = Modifier.clickable { selectedClient = client }, isSelected = selectedClient == client)
                    }
                }
            }
        },
        bottomBar = {
            Button(
                modifier = Modifier.fillMaxWidth().fillMaxWidth().padding(10.dp),
                onClick = {
                    addBudgetScreenViewModel.save(budgetName, selectedClient)
                    navController.navigate(ScreensRoute.BudgetsScreen.route)
                }
            ) {
                Text(text = "Salvar")
            }
        }
    )
}