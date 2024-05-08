package com.example.budgetbuddy.use_case.budget

import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.InvalidClientException
import com.example.budgetbuddy.domain.use_case.budget.InsertBudgetUseCase
import com.example.budgetbuddy.use_case.client.ClientRepositoryFake
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertBudgetUseCaseTest {
    private lateinit var insertBudgetUseCase: InsertBudgetUseCase
    private val budgetRepositoryFake = BudgetRepositoryFake()
    private val clientRepositoryFake = ClientRepositoryFake()

    @Before
    fun setUp(){
        insertBudgetUseCase = InsertBudgetUseCase(budgetRepositoryFake, clientRepositoryFake)
    }

    @Test
    fun insertBudgetUseCaseTest() = runBlocking {
        val budget = Budget(name = "Some Budget", clientId = "1", timestamp = System.currentTimeMillis())
        insertBudgetUseCase(budget)
        val findItem = budgetRepositoryFake.listBudgets.find { it.budgetId == budget.budgetId }
        assert(findItem != null)
        assertEquals(budget, findItem)
    }

    @Test(expected = InvalidClientException::class)
    fun throwExceptionTest() = runBlocking {
        val budget = Budget(name = "Some Budget", clientId = "X", timestamp = System.currentTimeMillis())
        insertBudgetUseCase(budget)
    }
}