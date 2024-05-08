package com.example.budgetbuddy.use_case.product

import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.repository.IRepository
import com.example.budgetbuddy.domain.use_case.product.GetProductUseCase
import com.example.budgetbuddy.domain.use_case.product.GetProductsUseCase
import com.example.budgetbuddy.domain.use_case.util.OrderType
import com.example.budgetbuddy.domain.use_case.util.ProductOrder
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetProductsUseCaseTest {
    @Mock
    private lateinit var productRepositoryMock: IRepository<Product, String>
    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var getProductUseCase: GetProductUseCase

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        runBlocking {
            `when`(productRepositoryMock.findOne("ID")).thenReturn(Product(productId = "ID", name = "Product 1", value = 150.00))
            val products = mutableListOf<Product>()
            for(i in 1..10){
                products.add(Product(productId = "ID$i", name = "Product $i", value = i.toDouble()))
            }
            val productsFlow = flow{
                emit(
                    products
                )
            }
            `when`(productRepositoryMock.findAll()).thenReturn(productsFlow)
        }
        getProductsUseCase = GetProductsUseCase(productRepositoryMock)
        getProductUseCase = GetProductUseCase(productRepositoryMock)
    }

    @Test
    fun getProductUseCase() = runBlocking {
        //whenever(productRepositoryMock.save(Product()))
        val product = getProductUseCase("ID")
        assert(product!=null)
        assertEquals(product!!.productId, "ID")
        assertEquals(product!!.name, "Product 1")
        assertEquals(product!!.value, 150.0)
    }

    @Test
    fun getProductsUseCaseByName() = runBlocking{
        val products = getProductsUseCase(ProductOrder.Name(OrderType.Ascending)).first()
        assertEquals(products[0].name, "Product 1")
        assertEquals(products[1].name, "Product 10")
    }

    @Test
    fun getProductsUseCaseByNameDescending() = runBlocking{
        val products = getProductsUseCase(ProductOrder.Name(OrderType.Descending)).first()
        assertEquals(products[0].name, "Product 9")
        assertEquals(products[1].name, "Product 8")
    }

    @Test
    fun getProductUseCaseByValueAscending() = runBlocking{
        val products = getProductsUseCase(ProductOrder.Value(OrderType.Ascending)).first()
        assertEquals(products[0].value, 1.0)
        assertEquals(products[1].value, 2.0)
    }

    @Test
    fun getProductUseCaseByValueDescending() = runBlocking{
        val products = getProductsUseCase(ProductOrder.Value(OrderType.Descending)).first()
        assertEquals(products[0].value, 10.0)
        assertEquals(products[1].value, 9.0)
    }
}