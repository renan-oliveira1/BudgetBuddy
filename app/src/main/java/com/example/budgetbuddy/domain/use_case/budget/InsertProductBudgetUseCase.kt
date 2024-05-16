package com.example.budgetbuddy.domain.use_case.budget

import com.example.budgetbuddy.domain.model.InvalidBudgetException
import com.example.budgetbuddy.domain.model.InvalidProductException
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.model.relations.BudgetProducts
import com.example.budgetbuddy.domain.model.relations.ProductsWithQuantity
import com.example.budgetbuddy.domain.repository.IBuggetRepository
import com.example.budgetbuddy.domain.repository.IRepository

class InsertProductBudgetUseCase(
    private val budgetRepository: IBuggetRepository,
    private val productRepository: IRepository<Product, String>
) {
    suspend operator fun invoke(budgetId: String, products: List<ProductsWithQuantity>){
        val listBudgetProduct = mutableListOf<BudgetProducts>()
        products.forEach{product ->
            productRepository.findOne(product.product.productId) ?: throw InvalidProductException("Product not found")
            listBudgetProduct.add(BudgetProducts(budgetId = budgetId, productId = product.product.productId, quantity = product.quantity))
        }
        budgetRepository.findOne(budgetId) ?: throw InvalidBudgetException("Budget not found!")
        budgetRepository.saveManyProducts(listBudgetProduct)
    }
}