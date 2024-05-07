package com.example.budgetbuddy.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgetbuddy.data.data_source.dao.BudgetDao
import com.example.budgetbuddy.data.data_source.dao.ClientDao
import com.example.budgetbuddy.data.data_source.dao.ProductDao
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.model.relations.BudgetProducts

@Database(
    entities = [Product::class, Client::class, Budget::class, BudgetProducts::class],
    version = 1,
    exportSchema = true
)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val budgetDao: BudgetDao
    abstract val clientDao: ClientDao

    companion object{
        const val DATABASE_NAME = "budget_db"
    }
}