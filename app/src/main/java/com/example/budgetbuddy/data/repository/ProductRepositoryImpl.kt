package com.example.budgetbuddy.data.repository

import com.example.budgetbuddy.data.data_source.dao.ProductDao
import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val productDao: ProductDao
): IRepository<Product, String> {
    override suspend fun save(product: Product) {
        productDao.save(product)
    }

    override fun findAll(): Flow<List<Product>> {
        return productDao.findAll()
    }

    override suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    override suspend fun findOne(id: String): Product? {
        return productDao.findOne(id)
    }

}