package com.example.budgetbuddy.use_case.client

import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.InvalidClientException
import com.example.budgetbuddy.domain.use_case.client.DeleteClientUseCase
import com.example.budgetbuddy.domain.use_case.product.DeleteProductUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteClientUseCaseTest {
    private lateinit var deleteProductUseCase: DeleteClientUseCase
    private val clientRepositoryFake = ClientRepositoryFake()
    private var client = Client(name = "RandomClient", timestamp = System.currentTimeMillis())

    @Before
    fun setUp(){
        deleteProductUseCase = DeleteClientUseCase(clientRepositoryFake)
        clientRepositoryFake.listClients.add(client)
    }

    @Test
    fun deleteClientUseCaseTest() = runBlocking {
        deleteProductUseCase(client)
        assert(!clientRepositoryFake.listClients.contains(client))
    }

    @Test(expected = InvalidClientException::class)
    fun throwExceptionTest() = runBlocking {
        val notSaveClient = Client(name = "NNNAme", timestamp = System.currentTimeMillis())
        deleteProductUseCase(notSaveClient)
    }
}