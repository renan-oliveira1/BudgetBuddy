package com.example.budgetbuddy.domain.use_case.budget

import com.example.budgetbuddy.domain.model.relations.BudgetWithProducts
import com.example.budgetbuddy.domain.model.relations.ProductsWithQuantity
import com.example.budgetbuddy.domain.repository.IBuggetRepository

class GetBudgetsProductsUseCase(
    private val budgetRepository: IBuggetRepository
) {
    operator fun invoke(budget: BudgetWithProducts): List<ProductsWithQuantity>{
        val listProducts = mutableListOf<ProductsWithQuantity>()
        budget.products.forEach{ product ->
            val quantity = budget.budgetProducts.find { budgetProduct -> budgetProduct.productId.equals(product.productId) }!!.quantity
            listProducts.add(ProductsWithQuantity(product = product, quantity = quantity))
        }
        return listProducts
    }
}