package com.example.budgetbuddy.domain.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.budgetbuddy.domain.model.Product

data class ProductsWithQuantity(
    val product: Product,
    var quantity: Int
)