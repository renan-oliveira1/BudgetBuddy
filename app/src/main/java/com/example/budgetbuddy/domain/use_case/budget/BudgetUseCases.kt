package com.example.budgetbuddy.domain.use_case.budget

data class BudgetUseCases(
    val insertBudgetUseCase: InsertBudgetUseCase,
    val getBudgetUseCase: GetBudgetUseCase,
    val getBudgetsUseCase: GetBudgetsUseCase,
    val getBudgetsProductsUseCase: GetBudgetsProductsUseCase,
    val insertBudgetProductUseCase: InsertProductBudgetUseCase,
    val deleteBudgetUseCase: DeleteBudgetUseCase
)