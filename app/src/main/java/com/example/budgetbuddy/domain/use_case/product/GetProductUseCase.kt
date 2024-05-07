package com.example.budgetbuddy.domain.use_case.product

import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class GetProductUseCase(
    private val productRepository: IRepository<Product, String>
) {
    suspend operator fun invoke(id: String): Product? {
        return productRepository.findOne(id)
    }
}