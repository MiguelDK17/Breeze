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
    //Pega as contas registradas no Room
    private val _contas = MutableStateFlow<List<Conta>>(emptyList())
    val contas: StateFlow<List<Conta>> = _contas.asStateFlow()
    //Verifica se houve contas encontradas
    private val _contasEncontradas = MutableStateFlow<Boolean>(false)
    val contasEncontradas: StateFlow<Boolean?> = _contasEncontradas.asStateFlow()
    //Armazena a data já traduzida
    private val _dataTraduzida = MutableStateFlow<String>("")
    val dataTraduzida: StateFlow<String> = _dataTraduzida.asStateFlow()
    //Filtra as contas
    private val _contasFiltradas = MutableStateFlow<List<Conta>>(emptyList())
    val contasFiltradas: StateFlow<List<Conta>> = _contasFiltradas.asStateFlow()

    //Busca as contas registradas no Room e manda pro ViewModel
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
                    //Se houver contas o estado é atualizado
                    if (contasFiltradas.isNotEmpty()) {
                        _contasFiltradas.value = contasFiltradas
                        //Salva a data já traduzida
                        salvaDataTraduzida(
                            traduzData(contasFiltradas.first().dateTime.toLocalDateTime().month.name)

                        )
                        _contasEncontradas.value = true
                        //Caso não houver atualiza o estado como false e uma mensagem é dispadada
                    } else {
                        _contasEncontradas.value = false
                        Toast.makeText(context, "Não há nenhuma conta registrada neste mês", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
    //Função que salva a data já traduzida
    fun salvaDataTraduzida(string: String){
        _dataTraduzida.value = string
    }

}