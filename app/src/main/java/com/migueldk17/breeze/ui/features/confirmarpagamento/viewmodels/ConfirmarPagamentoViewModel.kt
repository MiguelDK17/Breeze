package com.migueldk17.breeze.ui.features.confirmarpagamento.viewmodels

import android.content.Context
import android.util.Log
import android.content.ContentValues.TAG
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toDatabaseValue
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.ui.features.confirmarpagamento.state.ConfirmPaymentUIState
import com.migueldk17.breeze.ui.utils.ToastManager
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

    private val _nomeDaConta = MutableStateFlow("")
    val nomeDaConta: StateFlow<String> = _nomeDaConta.asStateFlow()

    private val _formaDePagamento = MutableStateFlow("Nenhum")
    val formaDePagamento: StateFlow<String> = _formaDePagamento.asStateFlow()

    private val _data = MutableStateFlow(LocalDate.now())
    val data: StateFlow<LocalDate> = _data.asStateFlow()

    private val _idDaConta: MutableStateFlow<Long> = MutableStateFlow(0)
    val idDaConta: StateFlow<Long> = _idDaConta.asStateFlow()

    private val _idDaParcela: MutableStateFlow<Long?> = MutableStateFlow(0)
    val idDaParcela: StateFlow<Long?> = _idDaParcela.asStateFlow()

    private val _numeroDaParcela: MutableStateFlow<Int> = MutableStateFlow(1)
    val numeroDaParcela: StateFlow<Int> = _numeroDaParcela.asStateFlow()

    private val _isLatestInstallment = MutableStateFlow(false)
    val isLatestInstallment: StateFlow<Boolean> = _isLatestInstallment.asStateFlow()

    fun setNomeDaConta(nome: String){
        _nomeDaConta.value = nome
    }

    fun setIdDaConta(id: Long){
        _idDaConta.value = id
    }

    fun setFormaDePagamento(value: String){
        _formaDePagamento.value = value
    }

    fun setIdDaParcela(long: Long){
        _idDaParcela.value = long
    }

    fun setNumeroDaParcela(int: Int){
        Log.d(TAG, "setNumeroDaParcela: numero da parcela no viewmodel: $int")
        _numeroDaParcela.value = int
    }

    fun setIsLatestInstallment(boolean: Boolean){
        Log.d(TAG, "setIsLatestInstallment: valor de isLatest: $boolean")
        _isLatestInstallment.value = boolean
    }

    fun efetuarPagamentos(isContaParcelada: Boolean){
        viewModelScope.launch {
            when{
                !isContaParcelada -> {
                    Log.d(TAG, "efetuarPagamentos: Tá caindo no !isContaParcelada")
                    efetuarPagamentoNaConta()
                }
                isContaParcelada && !_isLatestInstallment.value-> {
                    Log.d(TAG, "efetuarPagamentos: Tá caindo no segundo where")
                    efetuarPagamentoNasParcelas()
                }
                else -> {
                    Log.d(TAG, "efetuarPagamentos: Tá caindo no else")
                    efetuarPagamentoNasParcelas()
                    efetuarPagamentoNaConta()
                }
            }
        }
    }

    private fun efetuarPagamentoNaConta() {
        val data = _data.value.toDatabaseValue()
        val idDaConta = _idDaConta.value
        val formaDePagamento = _formaDePagamento.value
        viewModelScope.launch {
            contaRepository.efetuarPagamentoConta(data, idDaConta, formaDePagamento)
        }

    }

    private fun efetuarPagamentoNasParcelas(){
        val data = _data.value.toDatabaseValue()
        val nomeDaConta = _nomeDaConta.value
        val idDaConta = _idDaConta.value
        val idDaParcela = _idDaParcela.value
        val numeroDaParcela = _numeroDaParcela.value
        val formaDePagamento = _formaDePagamento.value
        val mensagemDeErro = "Operação mal sucedida. Verifique a conta e tente novamente"
        val mensagemDeSucesso = "Pagamento da ${numeroDaParcela}ª parcela da conta $nomeDaConta"

        viewModelScope.launch {
            val parcela = parcelaRepository.efetuarPagamentoParcela(data, idDaConta, idDaParcela!!, formaDePagamento) //Parcela está como Int porque o Room devolve booleano como Int
            if (parcela == 1) {
                ToastManager.showToast(context, mensagemDeSucesso)
            } else {
                ToastManager.showToast(context, mensagemDeErro)
            }
        }
    }
}
