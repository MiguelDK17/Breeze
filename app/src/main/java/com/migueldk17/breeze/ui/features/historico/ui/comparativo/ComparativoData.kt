package com.migueldk17.breeze.ui.features.historico.ui.comparativo

import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.domain.MovimentacaoDomain

sealed interface ComparativoData {
    data class Movimentacoes(
        val list: List<MovimentacaoDomain>,
        val totalReceitas: String,
        val totalDespesas: String,
        val saldoFinal: String
    ) : ComparativoData

    data class Categoria(val list: List<CategoryExpense>): ComparativoData
}