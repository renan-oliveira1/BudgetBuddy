package com.example.budgetbuddy.domain.use_case.util

sealed class ClientOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): ClientOrder(orderType)
    class Date(orderType: OrderType): ClientOrder(orderType)
    fun copyOrderType(orderType: OrderType): ClientOrder{
        return when(this){
            is ClientOrder.Name -> ClientOrder.Name(orderType)
            is ClientOrder.Date -> ClientOrder.Date(orderType)
        }
    }
}