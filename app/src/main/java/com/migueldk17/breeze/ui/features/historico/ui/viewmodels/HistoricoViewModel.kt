package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.content.Context
import android.util.Log
import android.content.ContentValues.TAG
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.MainActivity4
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.ui.utils.traduzData
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricoViewModel @Inject constructor(
    private val contaDao: ContaDao,
    private val contaRepository: ContaRepository,
    @ApplicationContext private val context: Context
): ViewModel(){
    //Pega as contas registradas no Room
    private val _contas = MutableStateFlow<List<Conta>>(emptyList())
    val contas: StateFlow<List<Conta>> = _contas.asStateFlow()
    //Verifica se houve contas encontradas
    private val _contasEncontradas = mutableMapOf<Long, StateFlow<UiState<List<Conta>>>>()
    //Armazena a data já traduzida
    private val _dataTraduzida = MutableStateFlow<String>("")
    val dataTraduzida: StateFlow<String> = _dataTraduzida.asStateFlow()
    //Filtra as contas

    private val _contasMensais = MutableStateFlow<UiState<ParcelaEntity>>(UiState.Loading)

    private val _navegarParaTela = MutableSharedFlow<String>()
    val navegarParaTela = _navegarParaTela.asSharedFlow()

    private val _mes = MutableStateFlow("")
    val mes: StateFlow<String> = _mes.asStateFlow()

    //Busca as contas registradas no Room e manda pro ViewModel
    init {
        viewModelScope.launch {
            contaDao.getContas()
                .collectLatest { lista ->
                    _contas.value = lista
                }
        }
    }

    fun buscaContasPorMes(mes: String){
        viewModelScope.launch {
            contaRepository.getContasPorMes(mes)
                .map { contas ->
                    when {
                        contas.isEmpty()-> {
                            Log.d(TAG, "buscaContasPorMes: $contas")
                            Log.d(TAG, "buscaContasPorMes: a lista tá realmente vazia")
                            Toast.makeText(context, "Não há contas registradas neste mês", Toast.LENGTH_SHORT).show()
                            UiState.Empty
                        }

                        else -> {
                            salvaDataTraduzida(
                                traduzData(contas.first().dateTime.toLocalDateTime().month.name)
                            )
                            _navegarParaTela.emit(_dataTraduzida.value)
                            UiState.Success(contas)
                        }
                    }
                }
                .catch {
                    Log.d(TAG, "buscaContasPorMes: caiu no catch: ${it.message}")
                    UiState.Error(it.message ?: "Erro desconhecido") }
                .onStart {
                    Log.d(TAG, "buscaContasPorMes: ainda tá no start")
                    emit(UiState.Loading) }
                .collect()
        }

    }

    //Função que salva a data já traduzida
    fun salvaDataTraduzida(string: String){
        _dataTraduzida.value = string
    }

}