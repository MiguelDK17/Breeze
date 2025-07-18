package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.content.Context
import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.ui.utils.ToastManager
import com.migueldk17.breeze.ui.utils.traduzData
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricoViewModel @Inject constructor(
    private val contaRepository: ContaRepository,
    private val parcelaRepository: ParcelaRepository,
    @ApplicationContext private val context: Context
): ViewModel(){
    //Pega as contas registradas no Room
    private val _contas = MutableStateFlow<List<Conta>>(emptyList())
    val contas: StateFlow<List<Conta>> = _contas.asStateFlow()
    //Armazena a data já traduzida
    private val _dataTraduzida = MutableStateFlow<String>("")
    val dataTraduzida: StateFlow<String> = _dataTraduzida.asStateFlow()
    //Filtra as contas

    private val _contasState = MutableStateFlow<UiState<List<Conta>>>(UiState.Loading)
    val contasState: StateFlow<UiState<List<Conta>>> = _contasState.asStateFlow()

    private val _navegarParaTela = MutableSharedFlow<Pair<String, String>>()
    val navegarParaTela = _navegarParaTela.asSharedFlow()


    private val _dataFormatada = MutableStateFlow("")
    val dataFormatada: StateFlow<String> = _dataFormatada.asStateFlow()

    //Função que salva a data já traduzida
    fun salvaDataTraduzida(string: String){
        _dataTraduzida.value = string
    }

    fun salvaDataFormatada(string: String){
        _dataFormatada.value = string
    }

    fun disparaNavegarParaTela(){
        val mes = _dataTraduzida.value
        val dataFormatada = _dataFormatada.value
        viewModelScope.launch {
            _navegarParaTela.emit(Pair(mes, dataFormatada))
        }
    }

}