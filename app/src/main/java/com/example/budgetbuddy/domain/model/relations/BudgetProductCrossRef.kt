package com.example.budgetbuddy.domain.model.relations

import androidx.room.Entity
import java.util.UUID

@Entity(primaryKeys = ["budgetId", "productId"])
data class BudgetProductCrossRef(
    val budgetId: String,
    val productId: String
)
