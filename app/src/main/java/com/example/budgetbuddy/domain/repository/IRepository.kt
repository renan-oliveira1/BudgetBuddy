package com.example.budgetbuddy.domain.repository

import kotlinx.coroutines.flow.Flow

interface IRepository<T, K> {
    suspend fun save(t: T)
    fun findAll(): Flow<List<T>>
    suspend fun findOne(id: K): T?
    suspend fun delete(t: T)
}