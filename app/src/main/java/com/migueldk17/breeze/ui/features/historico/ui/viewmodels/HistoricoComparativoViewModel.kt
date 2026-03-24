package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.ui.features.historico.ui.ComparativoFiltro
import com.migueldk17.breeze.ui.features.historico.ui.TipoData
import com.migueldk17.breeze.ui.utils.toApiFormat
import com.migueldk17.breeze.uistate.UiState
import com.migueldk17.breeze.usecases.GetMovimentacoesDoDiaUseCase
import com.migueldk17.breeze.usecases.GetMovimentacoesDoMesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HistoricoComparativoViewModel @Inject constructor(
    private val getMovimentacoesDoMesUseCase: GetMovimentacoesDoMesUseCase,
    private val getMovimentacoesDoDiaUseCase: GetMovimentacoesDoDiaUseCase
): ViewModel() {
    private val _filtro = MutableStateFlow(ComparativoFiltro())

    private val _tipoDeDados = MutableStateFlow(TipoData.MES)
    val tipoDeDados: StateFlow<TipoData> = _tipoDeDados.asStateFlow()


    private val _movimentacaoMesState: MutableStateFlow<UiState<List<MovimentacaoDomain>>> = MutableStateFlow(UiState.Loading)
    val movimentacaoMes: StateFlow<UiState<List<MovimentacaoDomain>>> = _movimentacaoMesState.asStateFlow()

    init {
        observaContasPoMes()
    }


    private fun observaContasPoMes(){
         viewModelScope.launch {
             _filtro
                 .flatMapLatest { filtro ->
                     when (filtro.tipoData) {
                         TipoData.MES -> {
                             _tipoDeDados.value = TipoData.MES
                             getMovimentacoesDoMesUseCase(filtro.data.orEmpty())
                         }
                         TipoData.DIA -> {
                             _tipoDeDados.value = TipoData.DIA
                             getMovimentacoesDoDiaUseCase(filtro.data.orEmpty())
                         }
                     }
                 }
                 .catch { e ->
                     _movimentacaoMesState.value = UiState.Error(e.message ?: "Erro desconhecido")

                 }
                 .collectLatest { list ->
                     val tipoData = _filtro.value.tipoData
                     val categoria = _filtro.value.categoria
                     when {
                         list.isEmpty() && tipoData == TipoData.MES -> {
                             _movimentacaoMesState.value = UiState.Empty
                         }
                         list.isEmpty() && tipoData == TipoData.DIA -> {
                             val data = _filtro.value.data!!.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM"))

                             _filtro.update { it.copy(
                                 data = data,
                                 tipoData = TipoData.MES) }
                         }
                         list.isEmpty() && categoria == null -> _movimentacaoMesState.value = UiState.Empty
                         else -> _movimentacaoMesState.value = UiState.Success(list)
                     }
                 }
         }

    }



    fun setMes (mes: String) {
        _filtro.update {
            it.copy(
                data = mes,
                tipoData = TipoData.MES
            )
        }
    }

    fun setDia(dia: String) {
        Log.d(TAG, "setDia: valor da data antes do update: ${_filtro.value}")
        _filtro.update {
            it.copy(
                data = dia,
                tipoData = TipoData.DIA
            )
        }
        Log.d(TAG, "setDia: valor da data depois do upgrade: ${_filtro.value}")
    }

    fun setCategoria(categoria: String) {
        _filtro.update {
            it.copy(
                categoria = categoria
            )
        }
    }
}