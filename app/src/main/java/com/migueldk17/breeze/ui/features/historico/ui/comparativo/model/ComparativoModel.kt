package com.migueldk17.breeze.ui.features.historico.ui.comparativo.model

import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.uistate.UiState

data class ComparativoModel(
    var listaDeMovimentacoesMensal: UiState<List<MovimentacaoDomain>> = UiState.Loading,
    var listaDeMovimentacoesDiaria: UiState<List<MovimentacaoDomain>> = UiState.Loading,
    var tipoDeDados: TipoDeDados = TipoDeDados.MES,
    var totalDeReceitas: String = "",
    var totalDeDespesas: String = "",
    var saldoFinal: String = "",
)