package com.example.budgetbuddy.data.data_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.budgetbuddy.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(product: Product)

    @Query("SELECT * FROM product")
    fun findAll(): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE productId = :id")
    suspend fun findOne(id: String): Product

    @Delete
    suspend fun delete(product: Product)
}