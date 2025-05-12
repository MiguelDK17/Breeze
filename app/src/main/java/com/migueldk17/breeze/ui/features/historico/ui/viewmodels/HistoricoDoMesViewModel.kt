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
    //Pega as contas do mes sem filtro
    fun getContasDoMes(): Flow<List<Conta>> {
        return repository.getContasPorMes(_data.value)
    }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    val historico: StateFlow<List<HistoricoDoDia>> = _data
        .flatMapLatest { mes -> 
            repository.getContasPorMes(mes.take(3)) //Pega as contas do mes
        }
        .map { contas ->
            contas
                .sortedBy { it.dateTime.toLocalDateTime() } //Filtra por data e hora
                .groupBy { it.dateTime.toLocalDateTime().toLocalDate() } //Agrupa por data
                .mapNotNull { (data, contasDoDia) ->
                    val contasOrdenadas = contasDoDia.sortedByDescending { it.dateTime } //Pega da mais recente a mais antiga
                    val primeira = contasOrdenadas.first() //Pega a primeira, que é a Conta Principal
                    val outras = contasOrdenadas.drop(1) //Remove a Conta Principal das outras contas
                        HistoricoDoDia(
                            data = data, //Data
                            contaPrincipal = primeira, //Conta Principal
                            outrasContas = outras //Outras Contas
                        )
                }
                .sortedByDescending { it.data } //Pega da mais recente a mais antiga
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    //Pega as primeiras três letras do mês
    fun setData(mes: String){
        _data.value = mes.take(3)
    }
    
}