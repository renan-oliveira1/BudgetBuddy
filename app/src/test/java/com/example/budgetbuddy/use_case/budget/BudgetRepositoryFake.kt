package com.example.budgetbuddy.use_case.budget

import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.model.relations.BudgetProducts
import com.example.budgetbuddy.domain.model.relations.BudgetWithProducts
import com.example.budgetbuddy.domain.repository.IBuggetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BudgetRepositoryFake: IBuggetRepository {
    val listBudgets = mutableListOf(
        Budget("1", "Budget 1", "1", System.currentTimeMillis()),
        Budget("2", "Budget 2", "2", System.currentTimeMillis()),
    )

    private val clientOne = Client("1", "Client 1", timestamp = System.currentTimeMillis())
    private val clientTwo = Client("2", "Client 2", timestamp = System.currentTimeMillis())
    private val productOne = Product("1", "Product 1", value = 10.0)

    val listBudgetProducts = mutableListOf(
        BudgetWithProducts(listBudgets[0], clientOne, mutableListOf(productOne), mutableListOf(BudgetProducts(budgetId = listBudgets[0].budgetId, productId = productOne.productId, quantity = 100))),
        BudgetWithProducts(listBudgets[1], clientTwo, mutableListOf(productOne), mutableListOf(BudgetProducts(budgetId = listBudgets[0].budgetId, productId = productOne.productId, quantity = 150)))
    )

    private val utilList = mutableListOf<BudgetProducts>()

    override suspend fun save(budget: Budget) {
        if (listBudgets.find { it.budgetId == budget.budgetId } != null){
            listBudgets.removeIf { it.clientId == budget.budgetId }
            listBudgets.add(budget)
            return
        }
        listBudgets.add(budget)
    }

    override fun findAll(): Flow<List<BudgetWithProducts>> {
        return flow{ emit(listBudgetProducts) }
    }

    override suspend fun findOne(id: String): BudgetWithProducts? {
        return listBudgetProducts.find { it.budget.budgetId == id }
    }

    override suspend fun delete(budget: Budget) {
        listBudgets.remove(budget)
    }

    override suspend fun saveProduct(budgetProducts: BudgetProducts) {
        utilList.add(budgetProducts)
    }

    override suspend fun saveManyProducts(list: List<BudgetProducts>) {
        utilList.addAll(list)
    }
}