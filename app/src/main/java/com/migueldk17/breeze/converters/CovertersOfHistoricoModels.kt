package com.migueldk17.breeze.converters

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.Receita
import com.migueldk17.breeze.ui.features.historico.model.GraficoDoDiaModel
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel

fun List<Conta>.toGraficoDoDia(): List<GraficoDoDiaModel> {
    return this.map { conta ->
        GraficoDoDiaModel(
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

fun List<Receita>.toGraficoDoDiaList(): List<GraficoDoDiaModel> {
    return this.map { receita ->
        GraficoDoDiaModel(
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

fun List<Conta>.toLinhaDoTempoModel(): List<LinhaDoTempoModel>{
    return this.map { conta ->
        LinhaDoTempoModel(
            id = conta.id,
            name = conta.name,
            valor = conta.valor,
            icon = conta.icon,
            dateTime = conta.dateTime.toLocalDateTime()
        )
    }
}

fun List<Receita>.toLinhaDoTempoModel(): List<LinhaDoTempoModel>{
    return this.map { receita ->
        LinhaDoTempoModel(
            id = receita.id,
            name = receita.descricao,
            valor = receita.valor,
            icon = receita.icon,
            dateTime = receita.data.toLocalDateTime()
        )
    }

}