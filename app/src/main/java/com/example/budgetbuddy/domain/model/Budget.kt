package com.example.budgetbuddy.domain.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Budget(
    @PrimaryKey val budgetId: String = GenerateIdHelper.generateRandomId(),
    @NonNull val name: String,
    val clientId: String,
    @NonNull val timestamp: Long
)

class InvalidBudgetException(message: String): Exception(message)
