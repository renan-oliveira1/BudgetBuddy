package com.example.budgetbuddy.domain.use_case.budget

import com.example.budgetbuddy.data.repository.BudgetRepositoryImpl
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.InvalidBudgetException
import com.example.budgetbuddy.domain.repository.IBuggetRepository

class DeleteBudgetUseCase(
    private val budgetRepository: IBuggetRepository
) {
    suspend operator fun invoke(budget: Budget){
        budgetRepository.findOne(budget.budgetId) ?: throw InvalidBudgetException("Budget not found!")
        budgetRepository.delete(budget)
    }
}