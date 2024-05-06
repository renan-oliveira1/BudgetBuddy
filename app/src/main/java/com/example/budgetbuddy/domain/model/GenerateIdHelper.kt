package com.example.budgetbuddy.domain.model

import java.util.UUID

object GenerateIdHelper {
    fun generateRandomId(): String {
        return UUID.randomUUID().toString()
    }
}