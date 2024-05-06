package com.example.budgetbuddy

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.budgetbuddy.data.data_source.dao.BudgetDao
import com.example.budgetbuddy.data.data_source.dao.ClientDao
import com.example.budgetbuddy.data.data_source.dao.ProductDao
import com.example.budgetbuddy.data.data_source.database.ApplicationDatabase
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.model.relations.BudgetProductCrossRef
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class AppDaosTest {
    private lateinit var applicationDatabase: ApplicationDatabase
    private lateinit var budgetDao: BudgetDao
    private lateinit var clientDao: ClientDao
    private lateinit var productDao: ProductDao
    private var productId = "IdProduct"
    private var clientId = "IdClient"
    private var budgetId = "IdBudget"


    @Before
    fun setUp(){
        applicationDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ApplicationDatabase::class.java
        ).allowMainThreadQueries().build()
        budgetDao = applicationDatabase.budgetDao
        clientDao = applicationDatabase.clientDao
        productDao = applicationDatabase.productDao
        val product = Product(productId = productId, name = "Produto 1", value = 100.00)
        val client = Client(clientId = clientId, name = "Renan")

        val budget = Budget(budgetId = budgetId, name = "Orcamento 2", clientId = clientId)
        runBlocking {
            productDao.save(product)
            clientDao.save(client)
            budgetDao.save(budget)
            budgetDao.saveProduct(BudgetProductCrossRef(budgetId = budgetId, productId = productId))
        }
    }

    @Test
    fun findOneProduct() = runBlocking{
        val product = productDao.findOne(productId)
        assertEquals(product.productId, productId)
        assertEquals(product.name, "Produto 1")
    }

    @Test
    fun findOneClient() = runBlocking {
        val client = clientDao.findOne(clientId)
        assertEquals(client.clientId, clientId)
        assertEquals(client.name, "Renan")
    }

    @Test
    fun findOneBudget() = runBlocking {
        val budget = budgetDao.findOne(budgetId)
        assertEquals(budget.budget.budgetId, budgetId)
        assertEquals(budget.client.clientId, clientId)
        assertEquals(budget.products.size, 1)
    }

    @Test
    fun findAllProduct() = runBlocking {
        val products = productDao.findAll().first()
        assertEquals(products.size, 1)
        assertEquals(products.get(0).productId, productId)
    }

    @Test
    fun findAllClient() = runBlocking{
        val clients = clientDao.findAll().first()
        assertEquals(clients.size, 1)
    }

    @Test
    fun findAllBudget() = runBlocking {
        val budgets = budgetDao.findAll().first()
        assertEquals(budgets.size, 1)
        assertEquals(budgets.get(0).budget.budgetId, budgetId)
        assertEquals(budgets.get(0).client.clientId, clientId)
        assertEquals(budgets.get(0).products.size, 1)
        assertEquals(budgets.get(0).products.get(0).productId, productId)
    }

    @After
    fun tearDown(){
        applicationDatabase.close()
    }
}