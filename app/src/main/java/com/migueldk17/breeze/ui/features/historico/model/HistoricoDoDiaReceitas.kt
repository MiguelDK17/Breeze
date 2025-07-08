package com.migueldk17.breeze.ui.features.historico.model

import com.migueldk17.breeze.entity.Receita
import java.time.LocalDate

data class HistoricoDoDiaReceitas(
    val data: LocalDate,
    val receitaPrincipal: Receita,
    val outrasReceitas: List<Receita>
)
