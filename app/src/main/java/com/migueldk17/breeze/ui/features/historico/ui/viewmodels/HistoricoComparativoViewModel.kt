package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.ui.features.historico.ui.ComparativoFiltro
import com.migueldk17.breeze.ui.features.historico.ui.TipoData
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
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HistoricoComparativoViewModel @Inject constructor(
    private val getMovimentacoesDoMesUseCase: GetMovimentacoesDoMesUseCase,
    private val getMovimentacoesDoDiaUseCase: GetMovimentacoesDoDiaUseCase
): ViewModel() {
    private val _filtro = MutableStateFlow(ComparativoFiltro())

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
                         TipoData.MES -> getMovimentacoesDoMesUseCase(filtro.data.orEmpty())
                         TipoData.DIA -> getMovimentacoesDoDiaUseCase(filtro.data.orEmpty())
                     }
                 }
                 .catch { e ->
                     _movimentacaoMesState.value = UiState.Error(e.message ?: "Erro desconhecido")

                 }
                 .collectLatest { list ->
                     when {
                         list.isEmpty() -> _movimentacaoMesState.value = UiState.Empty
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
        _filtro.update {
            it.copy(
                data = dia,
                tipoData = TipoData.DIA
            )
        }
    }

    fun setCategoria(categoria: String) {
        _filtro.update {
            it.copy(
                categoria = categoria
            )
        }
    }
}