package com.example.budgetbuddy.presentation.products.viewmodel

import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.use_case.util.OrderType
import com.example.budgetbuddy.domain.use_case.util.ProductOrder

data class ProductState(
    val products: List<Product> = listOf(),
    val orderProduct: ProductOrder = ProductOrder.Name(OrderType.Ascending)
)