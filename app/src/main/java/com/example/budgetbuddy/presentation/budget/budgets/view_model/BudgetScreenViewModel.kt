package com.example.budgetbuddy.presentation.budget.budgets.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.domain.model.Budget
import com.example.budgetbuddy.domain.use_case.budget.BudgetUseCases
import com.example.budgetbuddy.domain.use_case.budget.GetBudgetsUseCase
import com.example.budgetbuddy.domain.use_case.util.BudgetOrder
import com.example.budgetbuddy.domain.use_case.util.OrderType
import com.example.budgetbuddy.presentation.client.viewmodel.ClientState
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
class BudgetScreenViewModel @Inject constructor(
    val budgetsUseCase: BudgetUseCases
) : ViewModel(){
    private val _stateBudgets = mutableStateOf(BudgetState())
    val stateBudgets: State<BudgetState> = _stateBudgets
    private val _stateFilterVisibility = mutableStateOf(false)
    val stateVisibility: State<Boolean> = _stateFilterVisibility

    private val _showMessageSnackBar = MutableSharedFlow<ShowSnackbarEvent>()
    val showMessageSnackBar = _showMessageSnackBar.asSharedFlow()

    private var getBudgetsJob: Job? = null
    init {
        getBudgets();
    }

    fun getBudgets(budgetOrder: BudgetOrder = BudgetOrder.Name(OrderType.Ascending)) {
        getBudgetsJob?.cancel()
        getBudgetsJob = budgetsUseCase.getBudgetsUseCase(budgetOrder).onEach {
            _stateBudgets.value = stateBudgets.value.copy(
                budgets = it,
                budgetOrder = budgetOrder
            )
        }.launchIn(viewModelScope)
    }

    fun deleteBudget(budget: Budget){
        viewModelScope.launch {
            try{
                budgetsUseCase.deleteBudgetUseCase(budget)
            }catch (exception: Exception){
                _showMessageSnackBar.emit(ShowSnackbarEvent(exception.message ?: "Erro!"))
            }
        }
    }

    fun changeVisibility(){
        _stateFilterVisibility.value = !stateVisibility.value
    }
}