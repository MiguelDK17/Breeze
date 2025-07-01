package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.ui.utils.ToastManager
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
            contaRepository.getContasMes(mes)
                .map { contas ->
                    when {
                        contas.isEmpty()-> {
                            _contasState.value = UiState.Empty
                            ToastManager.showToast(context = context, message = "Não há contas registradas neste mês")
                        }

                        else -> {
                            salvaDataTraduzida(
                                traduzData(contas.first().dateTime.toLocalDateTime().month.name)
                            )
                            _contasState.value = UiState.Success(contas)
                            avancaParaMainActivity4()
                        }
                    }
                }
                .catch {
                    _contasState.value = UiState.Error(it.message ?: "Erro desconhecido") }
                .onStart {
                    _contasState.value = UiState.Loading
                }
                .collect()
        }

    }

    //Função que salva a data já traduzida
    fun salvaDataTraduzida(string: String){
        _dataTraduzida.value = string
    }

    fun salvaDataFormatada(string: String){
        _dataFormatada.value = string
    }

    fun avancaParaMainActivity4(){
        val mes = _dataTraduzida.value
        val dataFormatada = _dataFormatada.value
        viewModelScope.launch {
            _navegarParaTela.emit(Pair(mes, dataFormatada))
        }

    }

}