package com.migueldk17.breeze.ui.features.paginainicial.viewmodels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.dao.SaldoDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.Saldo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PaginaInicialViewModel @Inject constructor(
    private val saldoDao: SaldoDao,
    private val contaDao: ContaDao
): ViewModel() {
    //Banco de dados
    private val _saldo = MutableStateFlow<Saldo?>(null)
    val saldo: StateFlow<Saldo?> get() = _saldo

    //Variavwl que controla o estado de carregamente em PaginaInicial
    val carregando = MutableStateFlow(true)

    private val _conta = MutableStateFlow<List<Conta>>(emptyList())
    val conta: StateFlow<List<Conta>> = _conta

    private val _contaSelecionada = MutableStateFlow<Conta?>(null)
    val contaSelecionada: StateFlow<Conta?> = _contaSelecionada.asStateFlow()

    private val _showBottomSheet = MutableStateFlow<Boolean>(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()


    init {
        viewModelScope.launch {
            _saldo.value = saldoDao.getSaldo() ?: Saldo(valor = 0.00) //Valor inicial

            //Pega todas as contas registradas no Room
            contaDao.getContas()
                .collectLatest { lista ->
                    //Manda a lista de contas pra variavel _conta
                    _conta.value = lista

                    delay(500) //Adiciona um pequeno delay

                    carregando.value = false //Muda o valor para false, indicando que o carregamento terminou
                }
        }
    }
    fun atualizaBottomSheet(boolean: Boolean){
        _showBottomSheet.value = boolean
    }


    //Atualiza o saldo do usuário
    fun adicionaReceita(double: Double,
                        description: String = "",
                        date: LocalDateTime) {
        viewModelScope.launch {
            if (saldoDao.getSaldo() == null) {
                val saldoAtual = Saldo(id = 0, valor = double / 100)
                saldoDao.inserirSaldo(saldoAtual)
                Log.d(ContentValues.TAG, "atualizaSaldo: ${saldoDao.getSaldo()}")
                _saldo.value = saldoAtual
            } else {
                Log.d(ContentValues.TAG, "atualizaSaldo: Caiu no update")
                val saldoAtualizado = Saldo(id = 0, valor = double / 100)
                saldoDao.atualizarSaldo(saldoAtualizado)
                Log.d(ContentValues.TAG, "atualizaSaldo: ${saldoDao.getSaldo()}")
                _saldo.value = saldoAtualizado
            }
        }
    }

    //Pega as informações da conta selecionada em PaginaInicial baseada no ID fornecido
    fun pegaContaSelecionada(id: Int){
        viewModelScope.launch {
            _contaSelecionada.value = contaDao.getContaById(id)
        }
    }
    //Apaga a conta selecionada
    fun apagaConta(conta: Conta) {
        viewModelScope.launch {
            contaDao.apagarConta(conta)
        }
    }

}