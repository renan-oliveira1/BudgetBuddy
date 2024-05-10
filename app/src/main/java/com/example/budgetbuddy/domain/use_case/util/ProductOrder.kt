package com.example.budgetbuddy.domain.use_case.util

sealed class ProductOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): ProductOrder(orderType)
    class Value(orderType: OrderType): ProductOrder(orderType)

    fun copyOrderType(orderType: OrderType): ProductOrder{
        return when(this){
            is Name -> Name(orderType)
            is Value -> Value(orderType)
        }
    }
}