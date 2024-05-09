package com.example.budgetbuddy.presentation.products.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.use_case.product.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
): ViewModel(){

    private val _stateProducts = mutableStateOf<List<Product>>(listOf())
    val stateProduct: State<List<Product>> = _stateProducts

    private var getProductsJob: Job? = null

    init {
        getProducts()
    }

    fun onEvent(event: EventProduct){
        when(event){
            is EventProduct.OrderProduct -> {}
            is EventProduct.SaveOrUpdate -> {
                if(event.id == null){
                    save(name = event.name, value = event.value, description = event.description)
                }else{
                    update(id = event.id,name = event.name, value = event.value, description = event.description)
                }
            }
        }
    }

    fun getProducts(){
        getProductsJob?.cancel()
        getProductsJob = productUseCases.getProductsUseCase().onEach {
            _stateProducts.value = it
        }.launchIn(viewModelScope)
    }

    private fun save(name: String, description: String, value: String){
        val product = Product(name = name, description = description, value = value.toDouble())
        viewModelScope.launch {
            productUseCases.insertProductUseCase(product)
            getProducts()
        }
    }

    private fun update(id: String, name:String, description: String, value: String){
        val product = Product(productId = id,name = name, description = description, value = value.replace(",", ".").toDouble())
        viewModelScope.launch {
            productUseCases.updateProductUseCase(product)
            getProducts()
        }
    }

}