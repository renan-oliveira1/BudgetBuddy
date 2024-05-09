package com.example.budgetbuddy.presentation.products.view_model

import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.use_case.util.ProductOrder

sealed class EventProduct{
    class OrderProduct(val productOrder: ProductOrder): EventProduct()
    class SaveOrUpdate(val id: String? = null, val name:String, val description: String, val value: String): EventProduct()
}