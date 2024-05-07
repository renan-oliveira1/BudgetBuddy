package com.example.budgetbuddy.domain.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.relations.BudgetProductCrossRef
import com.example.budgetbuddy.domain.model.relations.BudgetWithProducts
import kotlinx.coroutines.flow.Flow

interface IBuggetRepository{
    suspend fun save(budget: Budget)
    fun findAll(): Flow<List<BudgetWithProducts>>
    suspend fun findOne(id: String): BudgetWithProducts?
    suspend fun delete(budget: Budget)
    suspend fun saveProduct(budgetProductCrossRef: BudgetProductCrossRef)
    suspend fun saveManyProducts(list: List<BudgetProductCrossRef>)

}