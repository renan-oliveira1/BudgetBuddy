package com.example.budgetbuddy.presentation.budget.add_budget.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.use_case.budget.BudgetUseCases
import com.example.budgetbuddy.domain.use_case.client.ClientUseCases
import com.example.budgetbuddy.domain.use_case.util.BudgetOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.compose.runtime.State
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.presentation.util.ShowSnackbarEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


@HiltViewModel
class AddBudgetScreenViewModel @Inject constructor(
    val budgetUseCases: BudgetUseCases,
    val clientUseCases: ClientUseCases
): ViewModel() {

    private val _stateClients = mutableStateOf<List<Client>>(listOf())
    val stateClients: State<List<Client>> = _stateClients

    private val _showMessageSnackBar = MutableSharedFlow<ShowSnackbarEvent>()
    val showMessageSnackBar = _showMessageSnackBar.asSharedFlow()
    private var getClientsJob: Job? = null

    init {
        getClients()
    }

    fun getClients(){
        getClientsJob?.cancel()
        getClientsJob = clientUseCases.getClientsUseCase().onEach {
            _stateClients.value = it
        }.launchIn(viewModelScope)
    }

    fun save(budgetName: String, client: Client){
        viewModelScope.launch {
            try {
                val budget = Budget(name = budgetName, clientId = client.clientId, timestamp = System.currentTimeMillis())
                budgetUseCases.insertBudgetUseCase(budget)
            }catch (exception: Exception){
                _showMessageSnackBar.emit(ShowSnackbarEvent(exception.message ?: "Erro!"))
            }
        }
    }

}