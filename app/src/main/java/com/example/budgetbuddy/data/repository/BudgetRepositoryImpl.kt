package com.example.budgetbuddy.data.repository

import com.example.budgetbuddy.data.data_source.dao.BudgetDao
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.relations.BudgetProducts
import com.example.budgetbuddy.domain.model.relations.BudgetWithProducts
import com.example.budgetbuddy.domain.repository.IBuggetRepository
import kotlinx.coroutines.flow.Flow

class BudgetRepositoryImpl(
    private val budgetDao: BudgetDao
): IBuggetRepository {
    override suspend fun save(budget: Budget) {
        budgetDao.save(budget)
    }

    override fun findAll(): Flow<List<BudgetWithProducts>> {
        return budgetDao.findAll()
    }

    override suspend fun findOne(id: String): BudgetWithProducts? {
        return budgetDao.findOne(id)
    }

    override suspend fun delete(budget: Budget) {
        budgetDao.delete(budget)
    }

    override suspend fun saveProduct(budgetProducts: BudgetProducts) {
        budgetDao.saveProduct(budgetProducts)
    }

    override suspend fun saveManyProducts(list: List<BudgetProducts>) {
        budgetDao.saveManyProducts(list)
    }
}