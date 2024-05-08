package com.example.budgetbuddy.use_case.budget

import com.example.budgetbuddy.domain.use_case.budget.GetBudgetUseCase
import com.example.budgetbuddy.domain.use_case.budget.GetBudgetsUseCase
import com.example.budgetbuddy.domain.use_case.util.BudgetOrder
import com.example.budgetbuddy.domain.use_case.util.OrderType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetBudgetUseCaseTest {
    private lateinit var getBudgetUseCase: GetBudgetUseCase
    private lateinit var getBudgetsUseCase: GetBudgetsUseCase
    private val budgetRepositoryFake = BudgetRepositoryFake()

    @Before
    fun setUp(){
        getBudgetsUseCase = GetBudgetsUseCase(budgetRepositoryFake)
        getBudgetUseCase = GetBudgetUseCase(budgetRepositoryFake)

    }

    @Test
    fun getBudgetUseCase() = runBlocking {
        val budget = getBudgetUseCase("1")
        assert(budget != null)
        assertEquals("Budget 1", budget!!.budget.name)
        assertEquals(1, budget!!.products.size)
    }

    @Test
    fun getBudgetsByNameUseCase() = runBlocking {
        budgetRepositoryFake.listBudgets.shuffle()
        budgetRepositoryFake.listBudgetProducts.shuffle()
        val budgets = getBudgetsUseCase(BudgetOrder.Name(OrderType.Ascending)).first()
        assertEquals("Budget 1", budgets[0].budget.name)
        assertEquals("Budget 2", budgets[1].budget.name)
    }

    @Test
    fun getBudgetsByClientUseCase() = runBlocking {
        budgetRepositoryFake.listBudgets.shuffle()
        budgetRepositoryFake.listBudgetProducts.shuffle()
        val budgets = getBudgetsUseCase(BudgetOrder.Client(OrderType.Ascending)).first()
        assertEquals("Client 1", budgets[0].client.name)
        assertEquals("Client 2", budgets[1].client.name)
    }

    @Test
    fun getBudgetsByClientDescendingUseCase() = runBlocking {
        budgetRepositoryFake.listBudgets.shuffle()
        budgetRepositoryFake.listBudgetProducts.shuffle()
        val budgets = getBudgetsUseCase(BudgetOrder.Client(OrderType.Descending)).first()
        assertEquals("Client 2", budgets[0].client.name)
        assertEquals("Client 1", budgets[1].client.name)
    }

    @Test
    fun getBudgetsByNameDescendingUseCase() = runBlocking {
        budgetRepositoryFake.listBudgets.shuffle()
        budgetRepositoryFake.listBudgetProducts.shuffle()
        val budgets = getBudgetsUseCase(BudgetOrder.Name(OrderType.Descending)).first()
        assertEquals("Budget 2", budgets[0].budget.name)
        assertEquals("Budget 1", budgets[1].budget.name)
    }

}