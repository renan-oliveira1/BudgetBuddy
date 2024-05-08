package com.example.budgetbuddy.use_case.product

import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.use_case.product.InsertProductUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertProductUseCaseTest {
    private lateinit var insertProductUseCase: InsertProductUseCase
    private val productRepositoryFake = ProductRepositoryFake()

    @Before
    fun setUp(){
        insertProductUseCase = InsertProductUseCase(productRepositoryFake)
    }

    @Test
    fun insertProduct() = runBlocking{
        val productInsertion = Product(name = "Product Insert", value = 99.0)
        insertProductUseCase(productInsertion)
        assert(productRepositoryFake.listProduct.contains(productInsertion))
    }
}