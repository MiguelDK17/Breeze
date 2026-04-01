package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.enums.TipoMovimentacao
import com.migueldk17.breeze.ui.features.historico.ui.ComparativoFiltro
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.ComparativoModel
import com.migueldk17.breeze.ui.utils.formatarValorEmReal
import com.migueldk17.breeze.uistate.UiState
import com.migueldk17.breeze.usecases.GetMovimentacoesDoDiaUseCase
import com.migueldk17.breeze.usecases.GetMovimentacoesDoMesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HistoricoComparativoViewModel @Inject constructor(
    private val getMovimentacoesDoMesUseCase: GetMovimentacoesDoMesUseCase,
    private val getMovimentacoesDoDiaUseCase: GetMovimentacoesDoDiaUseCase
): ViewModel() {
    private val _filtro = MutableStateFlow(ComparativoFiltro())
    private val _comparativoModel = MutableStateFlow(ComparativoModel())
    val comparativoModel = _comparativoModel.asStateFlow()

    private val _mesBackup = MutableStateFlow("")
    val mes = _mesBackup.asStateFlow()

    
    init {
        observaContasPoMes()
    }


    private fun observaContasPoMes(){
        viewModelScope.launch {
             _filtro
                 .flatMapLatest { filtro ->
                     when (_comparativoModel.value.tipoDeDados) {
                         TipoDeDados.MES -> {
                             Log.d(TAG, "observaContasPoMes: mes chamado")
                             getMovimentacoesDoMesUseCase(filtro.data.orEmpty())
                         }
                         TipoDeDados.DIA -> {
                             Log.d(TAG, "observaContasPoMes: dia chamado")
                             getMovimentacoesDoDiaUseCase(filtro.data.orEmpty())
                         }

                         TipoDeDados.CATEGORIA -> {
                             getMovimentacoesDoMesUseCase(filtro.data.orEmpty()) // Está aqui temporariamente enquanto essa função não é feita
                         }
                     }
                 }
                 .catch { e ->
                     _comparativoModel.update {
                         it.copy(
                             listaDeMovimentacoesMensal = UiState.Error(e.message ?: "Erro desconhecido")

                         )
                     }
                 }
                 .collectLatest { list ->
                     val categoria = _filtro.value.categoria
                     when {
                         list.isEmpty() && _comparativoModel.value.tipoDeDados == TipoDeDados.MES -> {
                             _comparativoModel.update {
                                 it.copy(
                                     listaDeMovimentacoesMensal = UiState.Empty
                                 )
                             }
                         }
                         list.isEmpty() && _comparativoModel.value.tipoDeDados == TipoDeDados.DIA -> {
                             val data = _mesBackup.value

                             _filtro.update { it.copy(
                                 data = data) }
                             Log.d(TAG, "observaContasPoMes: caiu em dia porém não há nenhuma conta registrada nesse dia, retornando para mês")
                         }
                         list.isEmpty() && categoria == null -> {
                             _comparativoModel.update {
                                 it.copy(
                                     listaDeMovimentacoesMensal = UiState.Empty
                                 )
                             }
                         }
                         list.isNotEmpty() && _filtro.value.tipoDeDados == TipoDeDados.MES -> {
                             _comparativoModel.update {
                                 it.copy(
                                     listaDeMovimentacoesMensal = UiState.Success(list)
                                 )
                             }
                             retornaValoresFinais(list.toImmutableList())
                             Log.d(TAG, "observaContasPoMes: lista não tá vazia e caiu em mes")
                         }
                         list.isNotEmpty() && _filtro.value.tipoDeDados == TipoDeDados.DIA -> {
                             _comparativoModel.update {
                                 it.copy(
                                     listaDeMovimentacoesDiaria = UiState.Success(list)
                                 )
                             }
                             Log.d(TAG, "observaContasPoMes: lista não tá vazia e caiu em DIA")
                             }
                         else -> {
                             Log.d(TAG, "observaContasPoMes: Inválido")
                         }
                     }
                 }
         }

    }

    fun setMes (mes: String) {
        _mesBackup.value = mes
        Log.d(TAG, "setMes: mesBackup tá assim: ${_mesBackup.value}")
        _filtro.update {
            it.copy(
                data = mes,
                tipoDeDados = TipoDeDados.MES

            )
        }

    }

    fun setDia(dia: String) {
        Log.d(TAG, "setDia: valor da data antes do update: ${_filtro.value}")
        _filtro.update {
            it.copy(
                data = dia,
                tipoDeDados = TipoDeDados.DIA
            )
        }
        Log.d(TAG, "setDia: valor da data depois do upgrade: ${_filtro.value}")
    }

    fun converteDiaEmMes() {
        Log.d(TAG, "converteDiaEmMes: função chamada")
        _filtro.update {
            it.copy(
                data = _mesBackup.value,
                tipoDeDados = TipoDeDados.DIA
            )
        }

    }
    fun setCategoria(categoria: String) {
        _filtro.update {
            it.copy(
                categoria = categoria,
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
}