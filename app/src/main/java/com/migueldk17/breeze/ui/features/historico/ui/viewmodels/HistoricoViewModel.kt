package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.entity.Conta
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
            if (mes == dataFormatada.month.name){
                print("retornou positivo")
            }
        }

    }

}