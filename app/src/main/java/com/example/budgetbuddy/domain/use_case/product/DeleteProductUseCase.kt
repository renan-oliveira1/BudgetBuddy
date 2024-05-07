package com.example.budgetbuddy.domain.use_case.product

import com.example.budgetbuddy.domain.model.InvalidProductException
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.repository.IRepository

class DeleteProductUseCase(
    private val productRepository: IRepository<Product, String>
) {
    suspend operator fun invoke(product: Product){
        productRepository.findOne(product.productId) ?: throw InvalidProductException("Product not found!!")
        productRepository.save(product)
    }
}