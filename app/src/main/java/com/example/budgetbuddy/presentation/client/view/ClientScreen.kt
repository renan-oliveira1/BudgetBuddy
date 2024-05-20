package com.example.budgetbuddy.presentation.client.view

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgetbuddy.presentation.products.view.components.ItemProduct
import com.example.budgetbuddy.presentation.products.viewmodel.ProductScreenViewModel
import com.example.budgetbuddy.presentation.util.ScreensRoute
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.R
import com.example.budgetbuddy.domain.use_case.util.OrderType
import com.example.budgetbuddy.domain.use_case.util.ProductOrder
import com.example.budgetbuddy.presentation.products.view.components.ProductOrderMenu
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.domain.use_case.util.ClientOrder
import com.example.budgetbuddy.presentation.client.view.components.ClientItem
import com.example.budgetbuddy.presentation.client.viewmodel.ClientScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientScreen(
    navController: NavController,
    clientScreenViewModel: ClientScreenViewModel = hiltViewModel()
) {
    val scaffoldState = remember{ SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }
    val stateItems = clientScreenViewModel.stateClients.value
    val statusVisibility = clientScreenViewModel.stateVisibility.value

    var clientName by remember { mutableStateOf("") }
    var clientCpf by remember { mutableStateOf("") }
    var dateClient: Long = 0
    var idClient: String? = null

    LaunchedEffect(key1 = true){
        clientScreenViewModel.showMessageSnackBar.collectLatest { event ->
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
                Text(text = "Clientes", color = Color.Black)
            },
            actions = {
                IconButton(onClick = { clientScreenViewModel.changeVisibility() }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_filter_list_alt_24), contentDescription = "Filter!", tint = Color.Black)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )

        )},

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
                        listOf("Nome", "Data Cadastro"),
                        filterCLick = {
                            when(it){
                                is String -> {
                                    if (it.equals("Nome")){
                                        clientScreenViewModel.getClients(ClientOrder.Name(stateItems.orderClient.orderType))
                                    }else if(it.equals("Data Cadastro")){
                                        clientScreenViewModel.getClients(ClientOrder.Date(stateItems.orderClient.orderType))
                                    }else if(it.equals("Crescente")){
                                        clientScreenViewModel.getClients(stateItems.orderClient.copyOrderType(OrderType.Ascending))
                                    }else{
                                        clientScreenViewModel.getClients(stateItems.orderClient.copyOrderType(OrderType.Descending))
                                    }
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                LazyColumn {
                    items(stateItems.clients){ item ->
                        ClientItem(
                            client = item,
                            modifier = Modifier.clickable {
                                clientName = item.name
                                clientCpf = item.cpf.toString()
                                dateClient = item.timestamp
                                idClient = item.clientId
                                showDialog = true
                            },
                            onDeleteClick = {
                                clientScreenViewModel.delete(item.clientId, item.name, item.cpf!!, item.timestamp)
                            }
                        )
                    }
                }
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        clientName = ""
                        clientCpf = ""
                        idClient = null
                        showDialog = false
                    },
                    title = {
                        Text(text = "Adicionar Cliente")
                    },
                    text = {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextField(
                                    value = clientName,
                                    onValueChange = { clientName = it },
                                    label = { Text("Nome do Cliente") },
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
                            TextField(
                                value = clientCpf,
                                onValueChange = { clientCpf = it },
                                label = { Text("Cpf (opcional)") },
                                modifier = Modifier.padding(5.dp),
                                shape = RoundedCornerShape(30.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    disabledTextColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if(idClient == null){
                                    clientScreenViewModel.save(name = clientName, cpf = clientCpf)
                                }else{
                                    clientScreenViewModel.update(id = idClient!!, name = clientName, cpf = clientCpf, date = dateClient)
                                }
                                clientName = ""
                                clientCpf = ""
                                idClient = null
                                showDialog = false
                            }
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                clientName = ""
                                clientCpf = ""
                                idClient = null
                                showDialog = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add shop")
            }
        }

    )
}