package com.example.budgetbuddy.use_case.client

import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.InvalidClientException
import com.example.budgetbuddy.domain.use_case.client.InsertClientUseCase
import com.example.budgetbuddy.domain.use_case.client.UpdateClientUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateClientUseCaseTest {
    private lateinit var updateClientUseCase: UpdateClientUseCase
    private val clientRepositoryFake = ClientRepositoryFake()
    private var client = Client(name = "RandomClient", timestamp = System.currentTimeMillis())

    @Before
    fun setUp(){
        updateClientUseCase = UpdateClientUseCase(clientRepositoryFake)
        clientRepositoryFake.listClients.add(client)
    }

    @Test
    fun updateClientUseCase() = runBlocking {
        val updateClient = Client(clientId = client.clientId, name = "New name updated", timestamp = client.timestamp)
        updateClientUseCase(updateClient)
        val  foundItem = clientRepositoryFake.listClients.find { it.clientId == updateClient.clientId }
        assertEquals("New name updated", foundItem!!.name)
    }

    @Test(expected = InvalidClientException::class)
    fun throwExceptionTest() = runBlocking {
        val notSaveClient = Client(clientId = "random", name = "New name updated", timestamp = client.timestamp)
        updateClientUseCase(notSaveClient)
    }
}