package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.data.local.entity.ParcelaEntity
import com.migueldk17.breeze.data.local.repository.ContaRepository
import com.migueldk17.breeze.data.local.repository.ParcelaRepository
import com.migueldk17.breeze.domain.GetParcelaPorIdUseCase
import com.migueldk17.breeze.domain.ObservarContasDoMesUseCase
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HistoricoDoMesViewModel @Inject constructor(
    private val observarContasDoMesUseCase: ObservarContasDoMesUseCase,
    private val getParcelaPorIdUseCase: GetParcelaPorIdUseCase,
    private val parcelaRepository: ParcelaRepository
): ViewModel() {
    //Pega a data do mes
    private val _data = MutableStateFlow("")
    val data: StateFlow<String> = _data.asStateFlow()

    private val _contasPorMes = MutableStateFlow<List<LinhaDoTempoModel>>(emptyList())
    val contasPorMes: StateFlow<List<LinhaDoTempoModel>> = _contasPorMes.asStateFlow()

    private val _listaVazia = MutableStateFlow(false)
    val listaVazia: StateFlow<Boolean> = _listaVazia.asStateFlow()


    private val _parcela = MutableStateFlow<UiState<ParcelaEntity>>(UiState.Loading)
    val parcela: StateFlow<UiState<ParcelaEntity>> = _parcela.asStateFlow()


    fun observarContaPorMes() {
        viewModelScope.launch {
            _data
                //Filtra caso a data seja vazia
                .filter { it.isNotBlank() && it.matches(Regex("""\d{4}-\d{2}%""")) }
                .flatMapLatest { mes ->
                    observarContasDoMesUseCase(mes)
                }
                .collectLatest { lista ->
                    _contasPorMes.value = lista
                    _listaVazia.value = lista.isEmpty()
                }
        }
    }

     fun buscaParcelaPorId(idParcela: Long) {
           viewModelScope.launch {
             _parcela.value = UiState.Loading
               _parcela.value = getParcelaPorIdUseCase(idParcela)
          }
     }

    val historico: StateFlow<List<HistoricoDoDia>> = _data
        .flatMapLatest{ mes ->
            observarContasDoMesUseCase(mes)
        }
        .map { todasAsContas ->

            todasAsContas
                .sortedBy { it.dateTime }
                .groupBy { it.dateTime.toLocalDate() }
                .mapNotNull { (data, contasDoDia) ->
                    val contasOrdenadas =
                        contasDoDia.sortedByDescending { it.dateTime }

                    val primeira = contasOrdenadas.first()
                    val outras = contasOrdenadas.drop(1)

                    HistoricoDoDia(
                        data = data,
                        primaryTimeline = primeira,
                        otherTimeline = outras
                    )
                }
                .sortedByDescending { it.data }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

       //Fica on quando a UI estiver ativa e tiver um observador, inicia com lista vazia
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())  //StateFlow que se inicia com o ViewModel e dura quando o ViewModel durar

    //Pega as primeiras três letras do mês
    fun setData(mes: String){
        _data.value = mes
    }
}