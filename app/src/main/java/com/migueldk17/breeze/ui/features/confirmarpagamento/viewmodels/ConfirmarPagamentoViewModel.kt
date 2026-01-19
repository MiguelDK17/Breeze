package com.migueldk17.breeze.ui.features.confirmarpagamento.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmarPagamentoViewModel @Inject constructor(
    private val contaRepository: ContaRepository,
    private val parcelaRepository: ParcelaRepository
): ViewModel() {
    private val _conta = MutableStateFlow<Conta?>(null)
    val conta: StateFlow<Conta?> = _conta.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<ContaRepository>>(UiState.Loading)
    val uiState: StateFlow<UiState<ContaRepository>> = _uiState.asStateFlow()


}
