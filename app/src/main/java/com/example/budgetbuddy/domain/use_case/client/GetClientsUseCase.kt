package com.example.budgetbuddy.domain.use_case.client

import com.example.budgetbuddy.data.repository.ClientRepositoryImpl
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.repository.IRepository
import com.example.budgetbuddy.domain.use_case.util.ClientOrder
import com.example.budgetbuddy.domain.use_case.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetClientsUseCase(
    private val clientRepositoryImpl: IRepository<Client, String>
) {
    operator fun invoke(clientOrder: ClientOrder = ClientOrder.Name(OrderType.Ascending)): Flow<List<Client>> {
        return clientRepositoryImpl.findAll().map { clients ->
            when(clientOrder.orderType){
                is OrderType.Ascending -> {
                    when(clientOrder){
                        is ClientOrder.Date -> clients.sortedBy { it.timestamp }
                        is ClientOrder.Name -> clients.sortedBy { it.name }
                    }
                }
                is OrderType.Descending -> {
                    when(clientOrder){
                        is ClientOrder.Date -> clients.sortedByDescending { it.timestamp }
                        is ClientOrder.Name -> clients.sortedByDescending { it.name }
                    }
                }
            }
        }
    }
}