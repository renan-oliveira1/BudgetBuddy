package com.example.budgetbuddy.use_case.product

import com.example.budgetbuddy.domain.model.Product
import com.example.budgetbuddy.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryFake: IRepository<Product, String> {
    val listProduct = mutableListOf<Product>(
        Product(productId = "1", name = "Product 1", value = 150.0),
        Product(productId = "2", name = "Product 2", value = 156.0),
        Product(productId = "3", name = "Product 3", value = 166.0)
    )
    override suspend fun save(t: Product) {
        if (listProduct.find { it.productId == t.productId } != null){
            listProduct.removeIf { it.productId == t.productId };
            listProduct.add(t)
            return
        }
        listProduct.add(t)
    }

    override fun findAll(): Flow<List<Product>> {
        return flow { emit(listProduct) }
    }

    override suspend fun delete(t: Product) {
        listProduct.remove(t)
    }

    override suspend fun findOne(id: String): Product? {
        return listProduct.find { product -> product.productId == id }
    }
}