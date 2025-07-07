package com.migueldk17.breeze.converters

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.Receita
import com.migueldk17.breeze.ui.features.historico.model.GraficoDoDia

fun List<Conta>.toGraficoDoDia(): List<GraficoDoDia> {
    return this.map { conta ->
        GraficoDoDia(
            dayOfMonth = conta.dateTime.toLocalDateTime().dayOfMonth,
            valor = conta.valor.toFloat(),
            brush = Brush.verticalGradient(
                colors = listOf(
                    conta.colorIcon.toColor(),
                    conta.colorCard.toColor()
                )
            )
        )

    }
}

fun List<Receita>.toGraficoDoDiaList(): List<GraficoDoDia> {
    return this.map { receita ->
        GraficoDoDia(
            dayOfMonth = receita.data.toLocalDate().dayOfMonth,
            valor = receita.valor.toFloat(),
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Green,
                    Color.Blue
                )
            )
        )
    }
}