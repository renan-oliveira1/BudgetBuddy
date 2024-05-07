package com.example.budgetbuddy.domain.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Product(
    @PrimaryKey val productId: String = GenerateIdHelper.generateRandomId(),
    val name: String,
    val description: String? = null,
    val value: Double
)

class InvalidProductException(message: String): Exception(message)