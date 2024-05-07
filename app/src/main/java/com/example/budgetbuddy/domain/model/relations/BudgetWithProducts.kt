package com.example.budgetbuddy.domain.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.Product

data class BudgetWithProducts (
    @Embedded val budget: Budget,
    @Relation(
        parentColumn = "clientId",
        entityColumn = "clientId"
    )
    val client: Client,
    @Relation(
        parentColumn = "budgetId",
        entityColumn = "productId",
        associateBy = Junction(BudgetProducts::class)
    )
    val products: List<Product>,
    @Relation(
        parentColumn = "budgetId",
        entityColumn = "budgetId",
        associateBy = Junction(BudgetProducts::class)
    )
    val budgetProducts: List<BudgetProducts>
){
    fun generateProductsWithQuantity(): List<ProductsWithQuantity>{
        val listProducts = mutableListOf<ProductsWithQuantity>()
        products.forEach{ product ->
            val quantity = budgetProducts.find { budgetProduct -> budgetProduct.productId.equals(product.productId) }!!.quantity
            listProducts.add(ProductsWithQuantity(product = product, quantity = quantity))
        }
        return listProducts
    }
}