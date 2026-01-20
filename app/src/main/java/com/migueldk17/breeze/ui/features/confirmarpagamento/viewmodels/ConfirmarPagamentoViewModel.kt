package com.migueldk17.breeze.ui.features.confirmarpagamento.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.ui.features.confirmarpagamento.state.ConfirmPaymentUIState
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmarPagamentoViewModel @Inject constructor(
    private val contaRepository: ContaRepository,
    private val parcelaRepository: ParcelaRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _conta = MutableStateFlow<Conta?>(null)
    val conta: StateFlow<Conta?> = _conta.asStateFlow()

    private val _formaDePagamento = MutableStateFlow("Nenhum")
    val formaDePagamento: StateFlow<String> = _formaDePagamento.asStateFlow()

    private val _uiState = MutableStateFlow(ConfirmPaymentUIState())
    val uiState: StateFlow<ConfirmPaymentUIState> = _uiState.asStateFlow()

    fun setFormaDePagamento(value: String){
        Toast.makeText(
            context, "O texto do value é: $value", Toast.LENGTH_SHORT
        ).show()
        _formaDePagamento.value = value
        Toast.makeText(
            context, "_formaDePagamento está assim: ${_formaDePagamento.value}", Toast.LENGTH_SHORT
        ).show()
    }






}
