package com.example.budgetbuddy.domain.use_case.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}