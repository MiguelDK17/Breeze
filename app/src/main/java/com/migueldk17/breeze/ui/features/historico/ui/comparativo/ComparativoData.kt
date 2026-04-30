package com.migueldk17.breeze.ui.features.historico.ui.comparativo

import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.domain.MovimentacaoDomain

/**
 * Contrato rígido que define os formatos de dados que podem ser exibidos na tela de Comparativo.
 * Sendo uma 'sealed interface', o compilador sabe exatamente quantas implementações existem.
 * Isso permite usar um bloco 'when' exaustivo lá no Jetpack Compose, obrigando o dev a tratar
 * tanto a aba de Movimentações quanto a de Categoria, sem precisar de um 'else' genérico.
 */
sealed interface ComparativoData {

    /**
     * Molde de dados usado quando o usuário está nas abas "Mês" ou "Dia".
     * Ele carrega a lista completa de histórico e os valores totais já mastigados
     * e formatados como String (ex: "R$ 1.500,00"). A UI vira apenas um "cabide" de dados,
     * sem nenhuma responsabilidade de calcular ou formatar moeda.
     */
    data class Movimentacoes(
        val list: List<MovimentacaoDomain>, // Lista crua para montar a RecyclerView/LazyColumn
        val totalReceitas: String,          // Pronto para o Card Verde
        val totalDespesas: String,          // Pronto para o Card Vermelho
        val saldoFinal: String              // Pronto para o Card de Saldo
    ) : ComparativoData

    /**
     * Molde de dados usado exclusivamente quando o usuário clica na aba "Categoria".
     * Como a visão de categorias é um resumo agregado (o gráfico de barras/pizza),
     * não precisamos de saldos ou receitas aqui, apenas a lista agrupada que vem direto do Room.
     */
    data class Categoria(
        val list: List<CategoryExpense>
    ): ComparativoData
}