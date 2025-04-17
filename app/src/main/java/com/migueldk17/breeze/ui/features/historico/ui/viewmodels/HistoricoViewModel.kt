package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.ui.utils.traduzData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricoViewModel @Inject constructor(
    private val contaDao: ContaDao,
    private val repository: ContaRepository,
    @ApplicationContext private val context: Context
): ViewModel(){
    private val _contas = MutableStateFlow<List<Conta>>(emptyList())
    val contas: StateFlow<List<Conta>> = _contas.asStateFlow()

    private val _contasEncontradas = MutableStateFlow<Boolean>(false)
    val contasEncontradas: StateFlow<Boolean?> = _contasEncontradas.asStateFlow()

    private val _dataTraduzida = MutableStateFlow<String>("")
    val dataTraduzida: StateFlow<String> = _dataTraduzida.asStateFlow()

    private val _contasFiltradas = MutableStateFlow<List<Conta>>(emptyList())
    val contasFiltradas: StateFlow<List<Conta>> = _contasFiltradas.asStateFlow()
    init {
        viewModelScope.launch {
            contaDao.getContas()
                .collectLatest { lista ->
                    _contas.value = lista
                }
        }
    }

    fun observarContasPorMes(mes: String){

        viewModelScope.launch {
            repository.getContasPorMes(mes)
                .collect { contasFiltradas ->
                    if (contasFiltradas.isNotEmpty()) {
                        _contasFiltradas.value = contasFiltradas
                        salvaDataTraduzida(
                            traduzData(contasFiltradas.first().dateTime.toLocalDateTime().month.name)

                        )
                        _contasEncontradas.value = true
                    } else {
                        _contasEncontradas.value = false
                        Toast.makeText(context, "Não há nenhuma conta registrada neste mês", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }

    fun salvaDataTraduzida(string: String){
        _dataTraduzida.value = string
    }

}