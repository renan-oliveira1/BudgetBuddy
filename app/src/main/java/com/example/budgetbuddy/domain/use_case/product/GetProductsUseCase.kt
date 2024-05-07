package com.example.budgetbuddy.domain.use_case.product

import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.repository.IRepository
import com.example.budgetbuddy.domain.use_case.util.OrderType
import com.example.budgetbuddy.domain.use_case.util.ProductOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetProductsUseCase(
    private val productRepository: IRepository<Product, String>
) {
    operator fun invoke(productOrder: ProductOrder = ProductOrder.Name(OrderType.Ascending)): Flow<List<Product>> {
        return productRepository.findAll().map { products ->
            when(productOrder.orderType){
                is OrderType.Ascending -> {
                    when(productOrder){
                        is ProductOrder.Name -> products.sortedBy { it.name }
                        is ProductOrder.Value -> products.sortedBy { it.value }
                    }
                }
                is OrderType.Descending -> {
                    when(productOrder){
                        is ProductOrder.Name -> products.sortedByDescending { it.name }
                        is ProductOrder.Value -> products.sortedByDescending { it.value }
                    }
                }
            }
        }
    }
}