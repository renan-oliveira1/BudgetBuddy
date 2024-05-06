package com.example.budgetbuddy.data.data_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(client: Client)

    @Query("SELECT * FROM client")
    fun findAll(): Flow<List<Client>>

    @Query("SELECT * FROM client WHERE clientId = :id")
    suspend fun findOne(id: String): Client

    @Delete
    suspend fun delete(client: Client)
}