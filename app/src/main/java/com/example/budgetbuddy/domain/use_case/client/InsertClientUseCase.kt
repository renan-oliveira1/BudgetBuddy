package com.example.budgetbuddy.domain.use_case.client

import br.com.colman.simplecpfvalidator.isCpf
import com.example.budgetbuddy.data.repository.ClientRepositoryImpl
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.InvalidClientException
import com.example.budgetbuddy.domain.repository.IRepository

class InsertClientUseCase(
    private val clientRepositoryImpl: IRepository<Client, String>
) {
    suspend operator fun invoke(client: Client){
        if (client.name.isBlank()){
            throw InvalidClientException("Client name cant be blank!")
        }
        if(!client.cpf.isNullOrBlank() && !client.cpf.isCpf()){
            throw InvalidClientException("Invalid client cpf!")
        }
        clientRepositoryImpl.save(client)
    }
}