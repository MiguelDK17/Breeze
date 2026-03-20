package com.migueldk17.breeze.ui.features.historico.ui

data class ComparativoFiltro(
    val data: String? = null,
    val tipoData: TipoData = TipoData.MES,
    val categoria: String? = null,
    val contaId: Long? = null,
)

enum class TipoData {
    DIA,
    MES
}