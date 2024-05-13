package com.example.budgetbuddy.domain.use_case.util

sealed class BudgetOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): BudgetOrder(orderType)
    class Client(orderType: OrderType): BudgetOrder(orderType)
    class Date(orderType: OrderType): BudgetOrder(orderType)

    fun copyOrderType(orderType: OrderType): BudgetOrder{
        return when(this){
            is BudgetOrder.Name -> BudgetOrder.Name(orderType)
            is BudgetOrder.Date -> BudgetOrder.Date(orderType)
            is BudgetOrder.Client -> BudgetOrder.Date(orderType)

        }
    }
}