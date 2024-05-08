package com.example.budgetbuddy.domain.use_case.budget

import com.example.budgetbuddy.domain.model.InvalidBudgetException
import com.example.budgetbuddy.domain.model.relations.BudgetWithProducts
import com.example.budgetbuddy.domain.repository.IBuggetRepository

class GetBudgetUseCase(
    private val budgetRepository: IBuggetRepository
) {
    suspend operator fun invoke(id: String): BudgetWithProducts?{
        val budget = budgetRepository.findOne(id) ?: throw InvalidBudgetException("Budget not found!")
        return budget
    }
}