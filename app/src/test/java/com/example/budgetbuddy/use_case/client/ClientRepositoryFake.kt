package com.example.budgetbuddy.use_case.client

import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClientRepositoryFake: IRepository<Client, String> {
    val listClients = mutableListOf<Client>(
        Client("1", "Client 1", timestamp = System.currentTimeMillis()),
        Client("2", "Client 2", timestamp = System.currentTimeMillis()),
        Client("3", "Client 3", timestamp = System.currentTimeMillis()),
        Client("4", "Client 4", timestamp = System.currentTimeMillis())
    )
    override suspend fun save(t: Client) {
        if (listClients.find { it.clientId == t.clientId } != null){
            listClients.removeIf { it.clientId == t.clientId }
            listClients.add(t)
            return
        }
        listClients.add(t)
    }

    override fun findAll(): Flow<List<Client>> {
        return flow{
            emit(listClients)
        }
    }

    override suspend fun delete(t: Client) {
        listClients.remove(t)
    }

    override suspend fun findOne(id: String): Client? {
        return listClients.find { it.clientId == id }
    }
}