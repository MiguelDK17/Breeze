package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.entity.Receita
import com.migueldk17.breeze.repository.ReceitaRepository
import com.migueldk17.breeze.uistate.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class HistoricoMesReceita @Inject constructor(
    private val receitaRepository: ReceitaRepository
): ViewModel() {
    private val _data = MutableStateFlow("")
    val data: MutableStateFlow<String> = _data

    private val _receitasPorMes = MutableStateFlow<List<Receita>>(emptyList())
    val receitasPorMes: StateFlow <List<Receita>> = _receitasPorMes.asStateFlow()

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
                                Log.d(TAG, "observarReceitasPorMes: Receitas vazias")
                            } else {
                                _receitasPorMes.value = receitas
                            }
                        }


                }
        }

    }

}