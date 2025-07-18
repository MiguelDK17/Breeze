package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.repository.ReceitaRepository
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricoReceitaViewModel @Inject constructor(
    private val receitaRepository: ReceitaRepository
): ViewModel() {
    private val _data = MutableStateFlow("")
    val data: MutableStateFlow<String> = _data

    private val _receitasPorMes = MutableStateFlow<UiState<List<LinhaDoTempoModel>>>(UiState.Loading)
    val receitaState: StateFlow <UiState<List<LinhaDoTempoModel>>> = _receitasPorMes.asStateFlow()

    fun setData(mes: String) {
        _data.value = mes
    }

    fun observarReceitasPorMes() {
        viewModelScope.launch {
            _data
                .filter { it.isNotBlank() && it.matches(Regex("""\d{4}-\d{2}%""")) }
                .collectLatest { mes ->
                    receitaRepository.getReceitasDoMes(mes)
                        .collectLatest { receitas ->
                            if (receitas.isEmpty()) {
                                _receitasPorMes.value = UiState.Empty
                                Log.d(TAG, "observarReceitasPorMes: Receitas vazias")
                            } else {
                                Log.d(TAG, "observarReceitasPorMes: Receitas recebidas")
                                val linhaDoTempoModel = receitas.map { receita ->
                                    LinhaDoTempoModel(
                                        id = receita.id,
                                        name = receita.descricao,
                                        valor = receita.valor,
                                        dateTime = receita.data.toLocalDate().atStartOfDay(),
                                        icon = receita.icon,
                                        isReceita = true
                                    )
                                }
                                _receitasPorMes.value = UiState.Success(linhaDoTempoModel)
                            }
                        }


                }
        }

    }

    fun organizaReceitas(lista: List<LinhaDoTempoModel>): List<HistoricoDoDia> {
         return lista
                .sortedBy { it.dateTime }
                .groupBy { it.dateTime.toLocalDate() }
                .mapNotNull { (data, receitasDoDia) ->
                    val receitasOrdenadas = receitasDoDia.sortedByDescending { it.dateTime }
                    val primeira = receitasOrdenadas.first()
                    val outras = receitasOrdenadas.drop(1)
                    Log.d(TAG, "organizaReceitas: primeira receita: $primeira")
                    Log.d(TAG, "organizaReceitas:outras receitas: $outras")

                    HistoricoDoDia(
                        data = data,
                        primaryTimeline = primeira,
                        otherTimeline = outras
                    )
                }
                .sortedByDescending { it.data }

        }


}