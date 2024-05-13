package com.example.budgetbuddy.presentation.products.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.use_case.product.ProductUseCases
import com.example.budgetbuddy.domain.use_case.util.OrderType
import com.example.budgetbuddy.domain.use_case.util.ProductOrder
import com.example.budgetbuddy.presentation.util.ShowSnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
): ViewModel(){

    private val _stateProducts = mutableStateOf<ProductState>(ProductState())
    val stateProduct: State<ProductState> = _stateProducts

    private val _showMessageSnackBar = MutableSharedFlow<ShowSnackbarEvent>()
    val showMessageSnackBar = _showMessageSnackBar.asSharedFlow()

    private val _stateFilterVisibility = mutableStateOf(false)
    val stateVisibility: State<Boolean> = _stateFilterVisibility

    private var getProductsJob: Job? = null

    init {
        getProducts()
    }

    fun changeVisibility(){
        _stateFilterVisibility.value = !stateVisibility.value
    }

    fun getProducts(productOrder: ProductOrder = ProductOrder.Name(OrderType.Ascending)){
        getProductsJob?.cancel()
        getProductsJob = productUseCases.getProductsUseCase(productOrder).onEach {
            _stateProducts.value = stateProduct.value.copy(
                products = it,
                orderProduct = productOrder
            )
        }.launchIn(viewModelScope)
    }

    fun save(name: String, description: String, value: String){
        val product = Product(name = name, description = description, value = value.toDouble())
        viewModelScope.launch {
            try{
                productUseCases.insertProductUseCase(product)
                getProducts(stateProduct.value.orderProduct)
            }catch (exception: Exception){
                _showMessageSnackBar.emit(ShowSnackbarEvent(exception.message ?: "Erro!!"))
            }
        }
    }

    fun update(id: String, name:String, description: String, value: String){
        val product = Product(productId = id,name = name, description = description, value = value.replace(",", ".").toDouble())
        viewModelScope.launch {
            try{
                productUseCases.updateProductUseCase(product)
                getProducts(stateProduct.value.orderProduct)
            }catch (exception: Exception){
                _showMessageSnackBar.emit(ShowSnackbarEvent(exception.message ?: "Erro!!"))
            }
        }
    }

    fun delete(id: String, name:String, description: String, value: String){
        val product = Product(productId = id,name = name, description = description, value = value.replace(",", ".").toDouble())
        viewModelScope.launch {
            try {
                productUseCases.deleteProductUseCase(product)
            }catch (exception: Exception){
                _showMessageSnackBar.emit(ShowSnackbarEvent(exception.message ?: "Erro!!"))
            }
        }
    }

}