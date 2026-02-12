package com.migueldk17.breeze.enums

enum class TipoMovimentacao(val valor: Int) {
    ENTRADA(0), //Receita
    SAIDA(1); //Despesa

    companion object {
        fun fromInt(value: Int) = values().first { it.valor == value }
        fun toInt(tipoMovimentacao: TipoMovimentacao) = tipoMovimentacao.valor
    }
}