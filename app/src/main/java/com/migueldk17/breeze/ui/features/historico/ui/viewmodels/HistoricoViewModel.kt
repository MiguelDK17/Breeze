package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.utils.traduzData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricoViewModel @Inject constructor(
    private val contaDao: ContaDao
): ViewModel(){
    private val _contas = MutableStateFlow<List<Conta>>(emptyList())
    val contas: StateFlow<List<Conta>> = _contas.asStateFlow()

    private val _contasEncontradas = MutableStateFlow<Boolean?>(null)
    val contasEncontradas: StateFlow<Boolean?> = _contasEncontradas.asStateFlow()

    private val _dataTraduzida = MutableStateFlow<String>("")
    val dataTraduzida: StateFlow<String> = _dataTraduzida.asStateFlow()

    private val _contasFiltradas = MutableStateFlow<List<Conta>>(emptyList())
    val contasFiltradas: StateFlow<List<Conta>> = _contasFiltradas.asStateFlow()
    init {
        viewModelScope.launch {
            contaDao.getConta()
                .collectLatest { lista ->
                    _contas.value = lista
                }
        }
    }

    fun filtraContasPorMes(mes: String){
        _contas.value.forEach { conta ->
            val dataFormatada = conta.dateTime.toLocalDateTime()
            val mesIngles = dataFormatada.month.name
            val dataTraduzida = traduzData(dataFormatada.month.name)

            if (mes == dataTraduzida.take(3)){
                _contasFiltradas.value = listOf(conta)
                salvaDataTraduzida(dataTraduzida)
                _contasEncontradas.value = true
            }
            else {
                println("tem algo errado")
                _contasEncontradas.value = false
            }
        }

    }

    fun salvaDataTraduzida(string: String){
        _dataTraduzida.value = string
    }

}