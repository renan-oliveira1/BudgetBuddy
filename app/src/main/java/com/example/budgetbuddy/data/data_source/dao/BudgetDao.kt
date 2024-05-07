package com.example.budgetbuddy.data.data_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.relations.BudgetProducts
import com.example.budgetbuddy.domain.model.relations.BudgetWithProducts
import com.example.budgetbuddy.domain.model.relations.ProductsWithQuantity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(budget: Budget)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(budgetProducts: BudgetProducts)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveManyProducts(list: List<BudgetProducts>)

    @Transaction
    @Query("SELECT * FROM budget")
    fun findAll(): Flow<List<BudgetWithProducts>>

    @Transaction
    @Query("SELECT * FROM budget WHERE budgetId = :id")
    suspend fun findOne(id: String): BudgetWithProducts

    @Delete
    suspend fun delete(budget: Budget)
}