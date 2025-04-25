package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.repository.ContaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.SortedMap
import javax.inject.Inject

@HiltViewModel
class HistoricoDoMesViewModel @Inject constructor(
    private val repository: ContaRepository
): ViewModel(){
    private val _data = MutableStateFlow("")
    val data: StateFlow<String> = _data.asStateFlow()

    private val contas = getContasDoMes()

    private val _contasAgrupadas: MutableStateFlow<Map<LocalDateTime, List<Conta>>> = MutableStateFlow(mapOf())
    val contasAgrupadas: StateFlow<Map<LocalDateTime, List<Conta>>> = _contasAgrupadas.asStateFlow()

    private val _contasOrdenadas: MutableStateFlow<SortedMap<LocalDateTime, List<Conta>>> = MutableStateFlow(sortedMapOf())
    val contasOrdenadas: StateFlow<SortedMap<LocalDateTime, List<Conta>>> = _contasOrdenadas.asStateFlow()

    fun getContasDoMes(): Flow<List<Conta>> {
        return repository.getContasPorMes(_data.value)
    }

    fun setData(mes: String){
        _data.value = mes.take(3)
    }

    fun setContasAgrupadas(contasAgrupadas: Map<LocalDateTime, List<Conta>>){
        _contasAgrupadas.value = contasAgrupadas
        ordenedAccountsBasedInLatestUntilFirst()
    }

    fun ordenedAccountsBasedInLatestUntilFirst(){
        _contasOrdenadas.value = _contasAgrupadas.value.toSortedMap(compareByDescending { it })
    }

}