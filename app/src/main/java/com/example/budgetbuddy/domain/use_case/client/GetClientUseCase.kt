package com.example.budgetbuddy.domain.use_case.client

import com.example.budgetbuddy.data.repository.ClientRepositoryImpl
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.repository.IRepository

class GetClientUseCase(
    private val clientRepositoryImpl: IRepository<Client, String>
) {
    suspend operator fun invoke(id: String): Client? {
        return clientRepositoryImpl.findOne(id)
    }
}