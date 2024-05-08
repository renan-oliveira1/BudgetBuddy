package com.example.budgetbuddy.use_case.client

import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.use_case.client.InsertClientUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertClientUseCaseTest {
    private lateinit var insertClientUseCase: InsertClientUseCase
    private val clientRepositoryFake = ClientRepositoryFake()

    @Before
    fun setUp(){
        insertClientUseCase = InsertClientUseCase(clientRepositoryFake)
    }

    @Test
    fun insertClientUseCaseTest() = runBlocking {
        var client = Client(name = "Client Random", timestamp = System.currentTimeMillis())
        insertClientUseCase(client)
        assert(clientRepositoryFake.listClients.contains(client))
    }
}