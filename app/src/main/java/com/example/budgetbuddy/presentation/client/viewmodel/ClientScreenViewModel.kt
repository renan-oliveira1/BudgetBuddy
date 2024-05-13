package com.example.budgetbuddy.presentation.client.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.domain.model.Client
import com.example.budgetbuddy.domain.use_case.client.ClientUseCases
import com.example.budgetbuddy.domain.use_case.util.ClientOrder
import com.example.budgetbuddy.domain.use_case.util.OrderType
import com.example.budgetbuddy.domain.use_case.util.ProductOrder
import com.example.budgetbuddy.presentation.util.ShowSnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientScreenViewModel @Inject constructor(
    val clientUseCases: ClientUseCases
): ViewModel(){

    private val _stateClients = mutableStateOf(ClientState())
    val stateClients: State<ClientState> = _stateClients
    private val _stateFilterVisibility = mutableStateOf(false)
    val stateVisibility: State<Boolean> = _stateFilterVisibility

    private var geClientsJob: Job? = null
    private val _showMessageSnackBar = MutableSharedFlow<ShowSnackbarEvent>()
    val showMessageSnackBar = _showMessageSnackBar.asSharedFlow()

    init {
        getClients()
    }

    fun changeVisibility(){
        _stateFilterVisibility.value = !stateVisibility.value
    }

    fun getClients(clientOrder: ClientOrder = ClientOrder.Name(OrderType.Ascending)){
        geClientsJob?.cancel()
        geClientsJob = clientUseCases.getClientsUseCase(clientOrder).onEach {
            _stateClients.value = stateClients.value.copy(
                clients = it,
                orderClient = clientOrder
            )
        }.launchIn(viewModelScope)
    }

    fun save(name: String, cpf: String){
        viewModelScope.launch {
            try {
                clientUseCases.insertClientUseCase(Client(name = name, cpf = cpf, timestamp = System.currentTimeMillis()))
                getClients(_stateClients.value.orderClient)
            }catch (exception: Exception){
                _showMessageSnackBar.emit(ShowSnackbarEvent(exception.message ?: "Erro!"))
            }
        }
    }

    fun update(id: String, name: String, cpf: String, date: Long){
        viewModelScope.launch {
            try {
                clientUseCases.updateClientUseCase(Client(clientId = id, name = name, cpf = cpf, timestamp = date))
                getClients(_stateClients.value.orderClient)
            }catch (exception: Exception){
                _showMessageSnackBar.emit(ShowSnackbarEvent(exception.message ?: "Erro!"))
            }
        }
    }

    fun delete(idClient: String, name: String, cpf: String, date: Long){
        viewModelScope.launch {
            try {
                clientUseCases.deleteClientUseCase(Client(clientId = idClient, name = name, cpf = cpf, timestamp = date))
                getClients(_stateClients.value.orderClient)
            }catch (exception: Exception){
                _showMessageSnackBar.emit(ShowSnackbarEvent(exception.message ?: "Erro!"))
            }
        }
    }

}