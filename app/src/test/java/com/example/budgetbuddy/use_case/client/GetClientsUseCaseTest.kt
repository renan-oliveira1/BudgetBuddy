package com.example.budgetbuddy.use_case.client

import com.example.budgetbuddy.domain.use_case.client.GetClientUseCase
import com.example.budgetbuddy.domain.use_case.client.GetClientsUseCase
import com.example.budgetbuddy.domain.use_case.util.ClientOrder
import com.example.budgetbuddy.domain.use_case.util.OrderType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetClientsUseCaseTest {
    private lateinit var getClientUseCase: GetClientUseCase
    private lateinit var getClientsUseCase: GetClientsUseCase
    private val repositoryFake = ClientRepositoryFake()

    @Before
    fun setUp(){
        getClientsUseCase = GetClientsUseCase(repositoryFake)
        getClientUseCase = GetClientUseCase(repositoryFake)
    }

    @Test
    fun getClientUseCaseTest() = runBlocking{
        val client = getClientUseCase("1")
        assert(client != null)
        assertEquals("1", client!!.clientId)
        assertEquals("Client 1", client!!.name)
    }

    @Test
    fun getClientsByNameUseCaseTest() = runBlocking {
        val clients = getClientsUseCase(ClientOrder.Name(OrderType.Ascending)).first()
        assertEquals("Client 1", clients[0].name)
        assertEquals("Client 2", clients[1].name)
        assertEquals("Client 3", clients[2].name)
        assertEquals("Client 4", clients[3].name)
    }

    @Test
    fun getClientsByNameDescendingUseCaseTest() = runBlocking {
        val clients = getClientsUseCase(ClientOrder.Name(OrderType.Descending)).first()
        assertEquals("Client 4", clients[0].name)
        assertEquals("Client 3", clients[1].name)
        assertEquals("Client 2", clients[2].name)
        assertEquals("Client 1", clients[3].name)
    }

    @Test
    fun getClientsByDateUseCaseTest() = runBlocking {
        val clients = getClientsUseCase(ClientOrder.Date(OrderType.Ascending)).first()
        assertEquals("Client 1", clients[0].name)
        assertEquals("Client 2", clients[1].name)
        assertEquals("Client 3", clients[2].name)
        assertEquals("Client 4", clients[3].name)
    }

}