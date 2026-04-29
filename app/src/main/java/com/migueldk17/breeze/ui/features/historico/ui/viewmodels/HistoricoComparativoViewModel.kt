package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.domain.model.BreezeInsight
import com.migueldk17.breeze.dto.CategoryTotalDto
import com.migueldk17.breeze.enums.TipoMovimentacao
import com.migueldk17.breeze.ui.features.historico.ui.ComparativoFiltro
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.ComparativoData
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.ComparativoModel
import com.migueldk17.breeze.ui.utils.formatarValorEmReal
import com.migueldk17.breeze.uistate.UiState
import com.migueldk17.breeze.usecases.GetCategoryTotalByMonthUseCase
import com.migueldk17.breeze.usecases.GetInsightMensalUseCase
import com.migueldk17.breeze.usecases.GetMovimentacoesDoDiaUseCase
import com.migueldk17.breeze.usecases.GetMovimentacoesDoMesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HistoricoComparativoViewModel @Inject constructor(
    private val getMovimentacoesDoMesUseCase: GetMovimentacoesDoMesUseCase,
    private val getMovimentacoesDoDiaUseCase: GetMovimentacoesDoDiaUseCase,
    private val getCategoryTotalByMonthUseCase: GetCategoryTotalByMonthUseCase,
    private val getInsightMensalUseCase: GetInsightMensalUseCase
): ViewModel() {
    private val _filtro = MutableStateFlow(ComparativoFiltro())
    private val _comparativoModel = MutableStateFlow(ComparativoModel())
    val comparativoModel = _comparativoModel.asStateFlow()

    private val _mesBackup = MutableStateFlow("")
    val mes = _mesBackup.asStateFlow()

    val uiState: StateFlow<UiState<ComparativoData>> = _filtro
        .flatMapLatest { filtro ->
            val data = filtro.data.orEmpty()
            when (filtro.tipoDeDados) {
                TipoDeDados.MES -> getMovimentacoesDoMesUseCase(data).map { processaMovimentacoes(it) }
                TipoDeDados.DIA -> getMovimentacoesDoDiaUseCase(data).map { processaMovimentacoes(it) }
                TipoDeDados.CATEGORIA -> getCategoryTotalByMonthUseCase(data).map { ComparativoData.Categoria(it) }
            }
        }
        .map { data ->
            val isEmpty = when (data) {
                is ComparativoData.Movimentacoes -> data.list.isEmpty()
                is ComparativoData.Categoria -> data.list.isEmpty()
            }
            if (isEmpty) {
                UiState.Empty
            } else {
                UiState.Success(data)
            }
        }
        .catch { emit(UiState.Error(it.message ?: "Erro desconhecido")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    val insightDoMes: StateFlow<BreezeInsight?> = getInsightMensalUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )


    fun setMes (mes: String) {
        _mesBackup.value = mes
        _filtro.update {
            it.copy(
                data = mes,
                tipoDeDados = TipoDeDados.MES

            )
        }
        _comparativoModel.update {
            it.copy(
                tipoDeDados = TipoDeDados.MES
            )
        }

    }

    fun setDia(dia: String) {
        _filtro.update {
            it.copy(
                data = dia,
                tipoDeDados = TipoDeDados.DIA
            )
        }
        _comparativoModel.update {
            it.copy(
                tipoDeDados = TipoDeDados.DIA
            )
        }

    }

    fun voltarParaMes() {
        _filtro.update {
            it.copy(
                data = _mesBackup.value,
                tipoDeDados = TipoDeDados.MES
            )
        }
        _comparativoModel.update {
            it.copy(
                tipoDeDados = TipoDeDados.MES
            )
        }

    }

    fun setCategoria() {
        _filtro.update {
            it.copy(
                tipoDeDados = TipoDeDados.CATEGORIA
            )
        }
        _comparativoModel.update {
            it.copy(
                tipoDeDados = TipoDeDados.CATEGORIA
            )
        }
    }

    private fun processaMovimentacoes(list: List<MovimentacaoDomain>): ComparativoData {
        val (entradas, saidas) = list.partition { it.tipo == TipoMovimentacao.ENTRADA }
        val totalEntradas = entradas.sumOf { it.valor }
        val totalSaidas = saidas.sumOf { it.valor }

        return ComparativoData.Movimentacoes(
            list = list,
            totalReceitas = totalEntradas.formatarValorEmReal(),
            totalDespesas = totalSaidas.formatarValorEmReal(),
            saldoFinal = (totalEntradas + totalSaidas).formatarValorEmReal()
        )
    }
}