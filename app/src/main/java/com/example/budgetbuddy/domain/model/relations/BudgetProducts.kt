package com.example.budgetbuddy.domain.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["budgetId", "productId"])
data class BudgetProducts(
    val budgetId: String,
    val productId: String,
    val quantity: Int
)
