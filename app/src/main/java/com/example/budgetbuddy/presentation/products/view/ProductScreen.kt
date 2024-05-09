package com.example.budgetbuddy.presentation.products.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.budgetbuddy.presentation.products.view_model.ProductScreenViewModel
import com.example.budgetbuddy.presentation.util.ScreensRoute
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.presentation.products.view_model.EventProduct

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavController,
    productScreenViewModel: ProductScreenViewModel = hiltViewModel()
) {
    val scaffoldState = remember{ SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }
    val stateItems = productScreenViewModel.stateProduct.value

    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productValue by remember { mutableStateOf("") }
    var idProduct: String? = null


    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState)
        },

        topBar = { TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.navigate(ScreensRoute.HomeScreen.route)}) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            },
            title = {
                Text(text = "Produtos")
            }
        )},

        content = {
                paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())
                ) {
                    LazyColumn {
                        items(stateItems){ item ->
                            ItemProduct(
                                product = item,
                                modifier = Modifier.clickable {
                                    productName = item.name
                                    productValue = String.format("%.2f", item.value)
                                    productDescription = item.description.toString()
                                    idProduct = item.productId
                                    showDialog = true
                                },
                                onDeleteClick = {
                                    idProduct = item.productId
                                    productName = item.name
                                    productValue = item.value.toString()
                                    productDescription = item.description ?: ""
                                }
                            )
                        }
                    }
                }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    title = {
                        Text(text = "Adicionar Produto")
                    },
                    text = {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextField(
                                    value = productName,
                                    onValueChange = { productName = it },
                                    label = { Text("Nome do Produto") },
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .background(Color.Transparent),
                                    textStyle = TextStyle(color = Color.Black),
                                    shape = RoundedCornerShape(30.dp)
                                )
                            }
                            TextField(
                                value = productValue,
                                onValueChange = { productValue = it },
                                label = { Text("Valor do produto") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                ),
                                modifier = Modifier.padding(5.dp),
                                shape = RoundedCornerShape(30.dp)
                            )
                            TextField(
                                value = productDescription,
                                onValueChange = { productDescription = it },
                                label = { Text("Descriçao (opcional)") },
                                modifier = Modifier.padding(5.dp),
                                shape = RoundedCornerShape(30.dp)
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if(idProduct == null){
                                    productScreenViewModel.onEvent(EventProduct.SaveOrUpdate(name = productName, value = productValue, description = productDescription))
                                }else{
                                    productScreenViewModel.onEvent(EventProduct.SaveOrUpdate(id = idProduct!!, name = productName, value = productValue, description = productDescription))
                                }
                                productName = ""
                                productValue = ""
                                productDescription = ""
                                showDialog = false
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text("Cancel")
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
