package com.example.budgetbuddy.domain.use_case.budget

import com.example.budgetbuddy.domain.model.InvalidBudgetException
import com.example.budgetbuddy.domain.model.InvalidProductException
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.model.relations.BudgetProducts
import com.example.budgetbuddy.domain.repository.IBuggetRepository
import com.example.budgetbuddy.domain.repository.IRepository

class InsertProductBudgetUseCase(
    private val budgetRepository: IBuggetRepository,
    private val productRepository: IRepository<Product, String>
) {
    suspend fun invoke(budgetId: String, productId: String, quantity: Int){
        productRepository.findOne(productId) ?: throw InvalidProductException("Product not found")
        budgetRepository.findOne(budgetId) ?: throw InvalidBudgetException("Budget not found!")
        budgetRepository.saveProduct(BudgetProducts(budgetId = budgetId, productId = productId, quantity = quantity))
    }
}