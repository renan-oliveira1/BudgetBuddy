package com.example.budgetbuddy.domain.use_case.util

sealed class BudgetOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): BudgetOrder(orderType)
    class Client(orderType: OrderType): BudgetOrder(orderType)
    class Date(orderType: OrderType): BudgetOrder(orderType)
}