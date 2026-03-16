package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.uistate.UiState
import com.migueldk17.breeze.usecases.GetMovimentacoesDoMesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HistoricoComparativoViewModel @Inject constructor(
    private val getMovimentacoesDoMesUseCase: GetMovimentacoesDoMesUseCase
): ViewModel() {
    private val _data = MutableStateFlow("")
    val data: StateFlow<String> = _data.asStateFlow()

    private val _movimentacaoMesState: MutableStateFlow<UiState<List<MovimentacaoDomain>>> = MutableStateFlow(UiState.Loading)
    val movimentacaoMes: StateFlow<UiState<List<MovimentacaoDomain>>> = _movimentacaoMesState.asStateFlow()


     fun observaContasPoMes(){
         viewModelScope.launch {
             _data
                 .flatMapLatest { mes ->
                     getMovimentacoesDoMesUseCase(mes)
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

    fun setData(mes: String) {
        _data.value = mes
    }


}