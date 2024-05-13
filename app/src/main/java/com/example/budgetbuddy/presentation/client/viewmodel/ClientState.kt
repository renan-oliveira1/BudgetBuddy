package com.example.budgetbuddy.presentation.client.viewmodel

import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.use_case.util.ClientOrder
import com.example.budgetbuddy.domain.use_case.util.OrderType

data class ClientState(
    val clients: List<Client> = listOf(),
    val orderClient: ClientOrder = ClientOrder.Name(OrderType.Ascending)
)
