package com.migueldk17.breeze.ui.features.historico.ui.comparativo.model

import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.uistate.UiState

data class ComparativoModel(
    val listaDeMovimentacoesMensal: UiState<List<MovimentacaoDomain>> = UiState.Loading,
    val listaDeMovimentacoesDiaria: UiState<List<MovimentacaoDomain>> = UiState.Loading,
    val listaDeMovimentacoesCategoria: UiState<List<CategoryExpense>> = UiState.Loading,
    val tipoDeDados: TipoDeDados = TipoDeDados.MES,
    val totalDeReceitas: String = "",
    val totalDeDespesas: String = "",
    val saldoFinal: String = "",
)