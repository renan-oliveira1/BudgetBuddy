package com.example.budgetbuddy.presentation.budget.add_budget.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.use_case.budget.BudgetUseCases
import com.example.budgetbuddy.domain.use_case.client.ClientUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.relations.ProductsWithQuantity
import com.example.budgetbuddy.domain.use_case.product.ProductUseCases
import com.example.budgetbuddy.presentation.util.ShowSnackbarEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


@HiltViewModel
class AddBudgetScreenViewModel @Inject constructor(
    val budgetUseCases: BudgetUseCases,
    val clientUseCases: ClientUseCases,
    val productsUseCases: ProductUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _stateClients = mutableStateOf<List<Client>>(listOf())
    val stateClients: State<List<Client>> = _stateClients

    private val _stateProducts = mutableStateOf<List<ProductsWithQuantity>>(listOf())
    val stateProducts: State<List<ProductsWithQuantity>> = _stateProducts

    private val _stateTotalValue= mutableDoubleStateOf(0.00)
    val stateTotalValue: State<Double> = _stateTotalValue

    private val _stateClientSelected = mutableStateOf<Client>(Client("", "", timestamp = System.currentTimeMillis()))
    val stateClientSelected: State<Client> = _stateClientSelected

    private val _showMessageSnackBar = MutableSharedFlow<ShowSnackbarEvent>()
    val showMessageSnackBar = _showMessageSnackBar.asSharedFlow()
    private var getClientsJob: Job? = null
    private var getProdutsJob: Job? = null
    private var budgetId: String? = null

    init {
        savedStateHandle.get<String?>("budgetId").let {budgetId ->
            getClients()
            getProducts()
            if(budgetId != ""){
                this.budgetId = budgetId
                viewModelScope.launch {
                    budgetUseCases.getBudgetUseCase(budgetId!!).also {
                        _stateClientSelected.value = it!!.client
                        budgetUseCases.getBudgetsProductsUseCase(it!!).forEach{ budgetProductsWithQuantityDAO ->
                            val matchedProduct = _stateProducts.value.find { productsWithQuantity -> productsWithQuantity.product.productId == budgetProductsWithQuantityDAO.product.productId }
                            matchedProduct?.quantity = budgetProductsWithQuantityDAO.quantity
                        }
                    }
                    calculateTotalValue()
                }
            }
        }
    }


    fun getClients(){
        getClientsJob?.cancel()
        getClientsJob = clientUseCases.getClientsUseCase().onEach {
            _stateClients.value = it
        }.launchIn(viewModelScope)
    }

    fun getProducts(){
        getProdutsJob?.cancel()
        getProdutsJob = productsUseCases.getProductsUseCase().onEach { listProducts ->
            val productsWithQuantity = listProducts.map { product -> ProductsWithQuantity(product, 0) }
            _stateProducts.value = productsWithQuantity
            calculateTotalValue()
        }.launchIn(viewModelScope)
    }

    fun calculateTotalValue(){
        var sum = 0.0
        stateProducts.value.forEach {
            sum += it.product.value*it.quantity
        }
        _stateTotalValue.value = sum
    }

    fun save(budgetName: String, client: Client, products: List<ProductsWithQuantity>){
        viewModelScope.launch {
            try {
                val budget = if(budgetId == null){
                        Budget(name = budgetName, clientId = client.clientId, timestamp = System.currentTimeMillis())
                    }else{
                        Budget(budgetId = budgetId!!, name = budgetName, clientId = client.clientId, timestamp = System.currentTimeMillis())
                    }
                budgetUseCases.insertBudgetUseCase(budget)
                budgetUseCases.insertBudgetProductUseCase(budget.budgetId, products.filter { it.quantity>0 })
            }catch (exception: Exception){
                _showMessageSnackBar.emit(ShowSnackbarEvent(exception.message ?: "Erro!"))
            }
        }
    }

    fun setSelectedClient(selectedClient: Client) {
        _stateClientSelected.value = selectedClient
    }

}