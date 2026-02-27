package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.data.local.entity.Conta
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoricoViewModel: ViewModel(){
    //Pega as contas registradas no Room
    private val _contas = MutableStateFlow<List<Conta>>(emptyList())
    val contas: StateFlow<List<Conta>> = _contas.asStateFlow()
    //Armazena a data já traduzida
    private val _dataTraduzida = MutableStateFlow<String>("")
    val dataTraduzida: StateFlow<String> = _dataTraduzida.asStateFlow()

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