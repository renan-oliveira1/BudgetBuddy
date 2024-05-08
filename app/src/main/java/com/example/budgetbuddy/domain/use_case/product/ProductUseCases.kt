package com.example.budgetbuddy.domain.use_case.product



data class ProductUseCases(
    val insertProductUseCase: InsertProductUseCase,
    val getProductUseCase: GetProductUseCase,
    val getProductsUseCase: GetProductsUseCase,
    val updateProductUseCase: UpdateProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase
)