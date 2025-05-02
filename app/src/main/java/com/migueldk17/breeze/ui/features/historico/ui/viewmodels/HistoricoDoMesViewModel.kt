package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import android.content.ContentValues.TAG
import javax.inject.Inject

@HiltViewModel
class HistoricoDoMesViewModel @Inject constructor(
    private val repository: ContaRepository
): ViewModel() {
    //Pega a data do mes
    private val _data = MutableStateFlow("")
    val data: StateFlow<String> = _data.asStateFlow()

    fun getContasDoMes(): Flow<List<Conta>> {
        return repository.getContasPorMes(_data.value)
    }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    val historico: StateFlow<List<HistoricoDoDia>> = _data
        .flatMapLatest { mes -> 
            repository.getContasPorMes(mes.take(3))
        }
        .map { contas ->
            contas
                .sortedBy { it.dateTime.toLocalDateTime() }
                .groupBy { it.dateTime.toLocalDateTime().toLocalDate() }
                .mapNotNull { (data, contasDoDia) ->
                    val contasOrdenadas = contasDoDia.sortedByDescending { it.dateTime }
                    val primeira = contasOrdenadas.first()
                    val outras = contasOrdenadas.drop(1)
                        HistoricoDoDia(
                            data = data,
                            contaPrincipal = primeira,
                            outrasContas = outras
                        )
                }
                .sortedByDescending { it.data }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    

    fun setData(mes: String){
        _data.value = mes.take(3)
    }
    
}