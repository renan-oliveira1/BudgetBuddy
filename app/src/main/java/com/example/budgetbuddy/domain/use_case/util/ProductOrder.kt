package com.example.budgetbuddy.domain.use_case.util

sealed class ProductOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): ProductOrder(orderType)
    class Value(orderType: OrderType): ProductOrder(orderType)
}