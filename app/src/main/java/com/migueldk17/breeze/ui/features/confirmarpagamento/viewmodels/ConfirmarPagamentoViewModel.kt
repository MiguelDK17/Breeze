package com.migueldk17.breeze.ui.features.confirmarpagamento.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toDatabaseValue
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
import java.time.LocalDate
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

    private val _data = MutableStateFlow(LocalDate.now())
    val data: StateFlow<LocalDate> = _data.asStateFlow()

    private val _idDaConta: MutableStateFlow<Long> = MutableStateFlow(0)
    val idDaConta: StateFlow<Long> = _idDaConta.asStateFlow()

    private val _idDaParcela: MutableStateFlow<Long?> = MutableStateFlow(0)
    val idDaParcela: StateFlow<Long?> = _idDaParcela.asStateFlow()

    private val _numeroDaParcela: MutableStateFlow<Long> = MutableStateFlow(0)
    val numeroDaParcela: StateFlow<Long> = _numeroDaParcela.asStateFlow()

    fun setIdDaConta(id: Long){
        _idDaConta.value = id
    }

    fun setFormaDePagamento(value: String){
        _formaDePagamento.value = value
        efetuarPagamentoNaConta()
    }

    fun setIdDaParcela(long: Long){
        _idDaParcela.value = long
    }

    fun setNumeroDaParcela(long: Long){
        _numeroDaParcela.value = long
    }

    private fun efetuarPagamentoNaConta() {
        val data = _data.value.toDatabaseValue()
        val idDaConta = _idDaConta.value
        val idDaParcela = _idDaParcela.value
        val formaDePagamento = _formaDePagamento.value
        viewModelScope.launch {
            if (_idDaParcela.value == null) {
                contaRepository
                    .efetuarPagamentoConta(data, idDaConta, formaDePagamento)
            } else {
                parcelaRepository.efetuarPagamentoParcela(data, idDaConta, idDaParcela!!)
            }
        }

    }






}
