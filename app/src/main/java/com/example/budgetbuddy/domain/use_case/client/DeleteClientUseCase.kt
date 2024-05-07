package com.example.budgetbuddy.domain.use_case.client

import com.example.budgetbuddy.data.repository.ClientRepositoryImpl
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.InvalidClientException
import com.example.budgetbuddy.domain.repository.IRepository

class DeleteClientUseCase(
    private val clientRepositoryImpl: IRepository<Client, String>
) {
    suspend operator fun invoke(client: Client){
        clientRepositoryImpl.findOne(client.clientId) ?: throw InvalidClientException("Client not found on database!")
        clientRepositoryImpl.delete(client)
    }
}