package com.migueldk17.breeze.ui.features.historico.model

import com.migueldk17.breeze.entity.Conta
import java.time.LocalDate

data class HistoricoDoDia(
    val data: LocalDate,
    val contaPrincipal: Conta,
    val outrasContas: List<Conta>
)
