package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.enums.TipoMovimentacao
import com.migueldk17.breeze.ui.features.historico.ui.ComparativoFiltro
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.ComparativoData
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.ComparativoModel
import com.migueldk17.breeze.ui.utils.formatarValorEmReal
import com.migueldk17.breeze.uistate.UiState
import com.migueldk17.breeze.usecases.GetCategoryTotalByMonthUseCase
import com.migueldk17.breeze.usecases.GetMovimentacoesDoDiaUseCase
import com.migueldk17.breeze.usecases.GetMovimentacoesDoMesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HistoricoComparativoViewModel @Inject constructor(
    private val getMovimentacoesDoMesUseCase: GetMovimentacoesDoMesUseCase,
    private val getMovimentacoesDoDiaUseCase: GetMovimentacoesDoDiaUseCase,
    private val getCategoryTotalByMonthUseCase: GetCategoryTotalByMonthUseCase
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
            UiState.Success(data) as UiState<ComparativoData>
        }
        .catch { emit(UiState.Error(it.message ?: "Erro desconhecido")) }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

//    init {
//        observaContasPoMes()
//    }
//
//
//    private fun observaContasPoMes(){
//        viewModelScope.launch {
//             combine(_filtro, comparativoModel) {filtro, comparativo ->
//                 filtro to comparativo.tipoDeDados
//             }.flatMapLatest { (filtro, tipo) ->
//                 when (tipo) {
//                     TipoDeDados.MES -> getMovimentacoesDoMesUseCase(filtro.data.orEmpty())
//                         .map { ComparativoData.Movimentacoes(it) }
//                     TipoDeDados.DIA -> getMovimentacoesDoDiaUseCase(filtro.data.orEmpty())
//                         .map { ComparativoData.Movimentacoes(it) }
//                     TipoDeDados.CATEGORIA -> getCategoryTotalByMonthUseCase(filtro.data.orEmpty())
//                         .map { ComparativoData.Categoria(it) }
//                 }
//             }
//                 .catch { e ->
//                     _comparativoModel.update {
//                         it.copy(
//                             listaDeMovimentacoesMensal = UiState.Error(e.message ?: "Erro desconhecido")
//
//                         )
//                     }
//                 }
//                 .collectLatest { list ->
//                     handleData(list)
//                 }
//         }
//
//    }

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
                tipoDeDados = TipoDeDados.DIA
            )
        }

    }

    fun setCategoria() {
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

    private fun handleData(data: ComparativoData) {
        when (data) {
            is ComparativoData.Movimentacoes -> {
                handleMovimentacoesResult(data.list)
            }
            is ComparativoData.Categoria -> {
                handleCategoriasResult(data.list)
            }
        }
    }

    private fun handleMovimentacoesResult(list: List<MovimentacaoDomain>) {
        val tipo = _comparativoModel.value.tipoDeDados
        handleResult(
            list = list,
            onEmpty = { handleEmptyState(tipo) },
            onSuccess = { handleSuccessState(it, tipo) }
        )
    }

    private fun handleCategoriasResult(list: List<CategoryExpense>) {
        handleResult(
            list = list,
            onEmpty = { updateCategoria(UiState.Empty) },
            onSuccess = { updateCategoria(UiState.Success(it)) }
        )
    }

    private fun handleEmptyState(tipo: TipoDeDados) {
        when (tipo) {
            TipoDeDados.MES -> {
                updateMensal(UiState.Empty)
            }
            TipoDeDados.DIA -> {
                voltarParaMes()
            }
            else -> {
                updateMensal(UiState.Empty)
            }
        }
    }

    private fun handleSuccessState(list: List<MovimentacaoDomain>, tipo: TipoDeDados) {
        when (tipo) {
            TipoDeDados.MES -> {
                updateMensal(UiState.Success(list))
                retornaValoresFinais(list.toImmutableList())
            }
            TipoDeDados.DIA -> {
                updateDiaria(UiState.Success(list))
            }
            else -> Unit
        }
    }

    private fun updateMensal(state: UiState<List<MovimentacaoDomain>>) {
        _comparativoModel.update {
            it.copy(
                listaDeMovimentacoesMensal = state
            )
        }
    }
    private fun updateDiaria(state: UiState<List<MovimentacaoDomain>>) {
        _comparativoModel.update {
            it.copy(
                listaDeMovimentacoesDiaria = state
            )
        }
    }

    private fun updateCategoria(state: UiState<List<CategoryExpense>>) {
        _comparativoModel.update {
            it.copy(
                listaDeMovimentacoesCategoria = state
            )
        }

    }

    private fun retornaValoresFinais(listMovimentacaoDomain: ImmutableList<MovimentacaoDomain>){
        val listPositiva = mutableListOf<BigDecimal>()
        val listNegativa = mutableListOf<BigDecimal>()

        for (i in listMovimentacaoDomain) {
            if (i.tipo == TipoMovimentacao.ENTRADA) listPositiva.add(i.valor) else listNegativa.add(i.valor)

        }

        val totalEntradas = listPositiva.sumOf { it }
        val totalSaidas = listNegativa.sumOf { it }
        val valorTotal = totalEntradas + totalSaidas


        val totalEntradasEmReais = totalEntradas.formatarValorEmReal()
        val totalSaidasEmReais = totalSaidas.formatarValorEmReal()
        val valorTotalEmReais = valorTotal.formatarValorEmReal()

        Log.d(TAG, "retornaValoresFinais: $totalEntradasEmReais")
        Log.d(TAG, "retornaValoresFinais: $totalSaidasEmReais")
        Log.d(TAG, "retornaValoresFinais: $valorTotalEmReais")

        _comparativoModel.update {
            it.copy(
                totalDeReceitas = totalEntradasEmReais,
                totalDeDespesas = totalSaidasEmReais,
                saldoFinal = valorTotalEmReais,
            )
        }
    }

    private fun <T> handleResult(
        list: List<T>,
        onEmpty: () -> Unit,
        onSuccess: (List<T>) -> Unit
    ) {
        if (list.isEmpty()) {
            onEmpty()
        } else {
            onSuccess(list)
        }
    }
}