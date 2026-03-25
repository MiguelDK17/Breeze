package com.migueldk17.breeze.ui.features.historico.ui.comparativo

import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.ui.features.historico.ui.TipoData
import com.migueldk17.breeze.uistate.UiState
import java.math.BigDecimal

data class ComparativoModel(
    var listaDeMovimentacoesMensal: UiState<List<MovimentacaoDomain>>,
    var listaDeMovimentacoesDiaria: UiState<List<MovimentacaoDomain>>,
    var tipoDeDados: TipoData = TipoData.MES,
    var totalDeReceitas: BigDecimal = BigDecimal.ZERO,
    var totalDeDespesas: BigDecimal = BigDecimal.ZERO,
    var saldoFinal: BigDecimal = BigDecimal.ZERO,
)