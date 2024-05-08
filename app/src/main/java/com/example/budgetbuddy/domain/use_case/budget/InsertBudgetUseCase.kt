package com.example.budgetbuddy.domain.use_case.budget

import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.model.InvalidBudgetException
import com.example.budgetbuddy.domain.model.InvalidClientException
import com.example.budgetbuddy.domain.repository.IBuggetRepository
import com.example.budgetbuddy.domain.repository.IRepository

class InsertBudgetUseCase(
    private val budgetRepository: IBuggetRepository,
    private val clientRepository: IRepository<Client, String>
) {
    suspend operator fun invoke(budget: Budget){
        if(budget.clientId.isBlank()){
            throw InvalidBudgetException("Invalid budget, client cant be empty!")
        }
        clientRepository.findOne(budget.clientId) ?: throw InvalidClientException("Client not found!")
        budgetRepository.save(budget)
    }
}