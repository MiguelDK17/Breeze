package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.domain.MovimentacaoDomain
import kotlinx.collections.immutable.ImmutableList


@Composable
fun ListGraficoCategoria(
    listMovimentacaoDomain: ImmutableList<MovimentacaoDomain>,
    modifier: Modifier = Modifier,
){
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listMovimentacaoDomain,
            key = { it.id }
        ) {
            val porcentagemBruta = it.totalPercentage
            val porcentagemLimpa = porcentagemBruta * 100
            val porcentagemFormatada = porcentagemLimpa.toString()

            ObjectGraficoCategoria(
                colorCard = it.colorCard,
                icon = it.icon.toBreezeIconsType(),
                iconColor = it.colorIcon,
                nomeCategoria = it.categoria,
                valorConta = it.valor,
                porcentagem = porcentagemFormatada,
                progress = it.progress,
                progressBrush = it.progressBrush
            )
        }
    }
}