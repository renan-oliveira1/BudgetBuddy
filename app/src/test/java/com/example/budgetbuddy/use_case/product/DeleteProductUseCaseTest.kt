package com.example.budgetbuddy.use_case.product

import com.example.budgetbuddy.domain.model.InvalidProductException
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.use_case.product.DeleteProductUseCase
import com.example.budgetbuddy.domain.use_case.product.UpdateProductUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteProductUseCaseTest {
    private lateinit var deleteProductUseCase: DeleteProductUseCase
    private val productRepositoryFake = ProductRepositoryFake()
    private var product: Product = Product(name = "Product Insert", value = 99.0)

    @Before
    fun setUp(){
        deleteProductUseCase = DeleteProductUseCase(productRepositoryFake)
        productRepositoryFake.listProduct.add(product!!)
    }

    @Test
    fun deleteProductUseCaseTest() = runBlocking {
        deleteProductUseCase(product)
        assert(!productRepositoryFake.listProduct.contains(product))
    }

    @Test(expected = InvalidProductException::class)
    fun deleteThrowException() = runBlocking {
        deleteProductUseCase(Product("111", "Name", "Desscrption", 10.11))
        assert(!productRepositoryFake.listProduct.contains(product))
    }
}