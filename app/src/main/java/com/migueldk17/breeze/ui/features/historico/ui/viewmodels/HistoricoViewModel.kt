package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.ui.utils.traduzData
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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
    //Verifica se houve contas encontradas
    private val _contasEncontradas = mutableMapOf<Long, StateFlow<UiState<List<Conta>>>>()
    //Armazena a data já traduzida
    private val _dataTraduzida = MutableStateFlow<String>("")
    val dataTraduzida: StateFlow<String> = _dataTraduzida.asStateFlow()
    //Filtra as contas
    private val _contasFiltradas = MutableStateFlow<List<Conta>>(emptyList())
    val contasFiltradas: StateFlow<List<Conta>> = _contasFiltradas.asStateFlow()

    private val _contasMensais = MutableStateFlow<UiState<ParcelaEntity>>(UiState.Loading)

    private val _mes = MutableStateFlow("")
    val mes: StateFlow<String> = _mes.asStateFlow()

    //Busca as contas registradas no Room e manda pro ViewModel
    init {
        viewModelScope.launch {
//            contaDao.getContas()
//                .collectLatest { lista ->
//                    _contas.value = lista
//                }
        }
    }
    fun salvaMes(mes: String){
        _mes.value = mes
    }

    fun buscaContasPorMes(): Flow<UiState<List<Conta>?>> {
        return contaRepository.getContasPorMes(_mes.value)
            .map { it?.let { UiState.Success(it) } ?: UiState.Empty }
            .catch { emit(UiState.Error(it.message ?: "Erro desconhecido")) }
            .onStart { emit(UiState.Loading) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, UiState.Loading)
    }

    //Função que salva a data já traduzida
    fun salvaDataTraduzida(string: String){
        _dataTraduzida.value = string
    }

}