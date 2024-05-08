package com.example.budgetbuddy.domain.use_case.client

data class ClientUseCases(
    val insertClientUseCase: InsertClientUseCase,
    val getClientUseCase: GetClientUseCase,
    val getClientsUseCase: GetClientsUseCase,
    val updateClientUseCase: UpdateClientUseCase,
    val deleteClientUseCase: DeleteClientUseCase
)
