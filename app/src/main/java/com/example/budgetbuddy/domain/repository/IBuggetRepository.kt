package com.example.budgetbuddy.domain.repository

import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.relations.BudgetProducts
import com.example.budgetbuddy.domain.model.relations.BudgetWithProducts
import kotlinx.coroutines.flow.Flow

interface IBuggetRepository{
    suspend fun save(budget: Budget)
    fun findAll(): Flow<List<BudgetWithProducts>>
    suspend fun findOne(id: String): BudgetWithProducts?
    suspend fun delete(budget: Budget)
    suspend fun saveProduct(budgetProducts: BudgetProducts)
    suspend fun saveManyProducts(list: List<BudgetProducts>)

}