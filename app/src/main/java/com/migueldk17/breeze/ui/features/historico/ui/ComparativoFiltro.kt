package com.migueldk17.breeze.ui.features.historico.ui

data class ComparativoFiltro(
    val data: String? = null,
    val tipoDeDados: TipoDeDados = TipoDeDados.MES,
    val categoria: String? = null,
    val contaId: Long? = null,
)

enum class TipoDeDados {
    DIA,
    MES
}