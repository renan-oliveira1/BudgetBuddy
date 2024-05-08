package com.example.budgetbuddy.domain.use_case.budget

import com.example.budgetbuddy.domain.model.relations.BudgetWithProducts
import com.example.budgetbuddy.domain.repository.IBuggetRepository
import com.example.budgetbuddy.domain.use_case.util.BudgetOrder
import com.example.budgetbuddy.domain.use_case.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBudgetsUseCase(
    private val budgetRepository: IBuggetRepository
) {
    operator fun invoke(budgetOrder: BudgetOrder = BudgetOrder.Date(OrderType.Ascending)): Flow<List<BudgetWithProducts>>{
        return budgetRepository.findAll().map { budgets ->
            when(budgetOrder.orderType){
                is OrderType.Ascending -> {
                    when(budgetOrder){
                        is BudgetOrder.Client -> budgets.sortedBy { it.client.name }
                        is BudgetOrder.Date -> budgets.sortedBy { it.budget.timestamp }
                        is BudgetOrder.Name -> budgets.sortedBy { it.budget.name }
                    }
                }
                is OrderType.Descending -> {
                    when(budgetOrder){
                        is BudgetOrder.Client -> budgets.sortedByDescending { it.client.name }
                        is BudgetOrder.Date -> budgets.sortedByDescending { it.budget.timestamp }
                        is BudgetOrder.Name -> budgets.sortedByDescending { it.budget.name }
                    }
                }
            }
        }
    }
}