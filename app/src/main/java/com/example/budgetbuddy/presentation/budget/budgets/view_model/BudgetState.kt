package com.example.budgetbuddy.presentation.budget.budgets.view_model

import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.relations.BudgetWithProducts
import com.example.budgetbuddy.domain.use_case.util.BudgetOrder
import com.example.budgetbuddy.domain.use_case.util.OrderType

data class BudgetState(
    val budgets: List<BudgetWithProducts> = listOf(),
    val budgetOrder: BudgetOrder = BudgetOrder.Name(OrderType.Ascending)
)