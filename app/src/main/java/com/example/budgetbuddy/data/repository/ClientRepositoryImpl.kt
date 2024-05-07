package com.example.budgetbuddy.data.repository

import com.example.budgetbuddy.data.data_source.dao.ClientDao
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class ClientRepositoryImpl(
    private val clientDao: ClientDao
): IRepository<Client, String> {
    override suspend fun save(client: Client){
        clientDao.save(client)
    }

    override fun findAll(): Flow<List<Client>> {
        return clientDao.findAll()
    }

    override suspend fun delete(client: Client) {
        clientDao.delete(client)
    }

    override suspend fun findOne(id: String): Client? {
        return clientDao.findOne(id)
    }
}