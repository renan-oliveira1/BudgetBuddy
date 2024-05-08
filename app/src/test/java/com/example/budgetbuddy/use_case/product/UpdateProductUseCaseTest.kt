package com.example.budgetbuddy.use_case.product

import com.example.budgetbuddy.domain.model.InvalidProductException
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.use_case.product.UpdateProductUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateProductUseCaseTest {
    private lateinit var updateProductUseCase: UpdateProductUseCase
    private val productRepositoryFake = ProductRepositoryFake()
    private var product: Product = Product(name = "Product Insert", value = 99.0)

    @Before
    fun setUp(){
        updateProductUseCase = UpdateProductUseCase(productRepositoryFake)
        productRepositoryFake.listProduct.add(product!!)
    }

    @Test
    fun updateProductUseCase() = runBlocking{
        val updatedProduct = Product(productId = product.productId, name = "New Name", description = "New Description", value = 10.0)
        updateProductUseCase(updatedProduct)
        val foundItem = productRepositoryFake.listProduct.find { it.productId == product.productId }
        assertEquals(foundItem!!.name, "New Name")
        assertEquals(foundItem!!.description, "New Description")
        assertEquals(foundItem!!.value, 10.0)
    }

    @Test(expected = InvalidProductException::class)
    fun updateThrowException() = runBlocking{
        val updatedProduct = Product(productId = "TESTE", name = "New Namee", description = "New Description", value = 10.0)
        updateProductUseCase(updatedProduct)
    }

}