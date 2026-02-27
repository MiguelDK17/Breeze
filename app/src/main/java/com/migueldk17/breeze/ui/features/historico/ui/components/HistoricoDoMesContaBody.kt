package com.migueldk17.breeze.ui.features.historico.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HistoricoDoMesContaBody(
    listContas: ImmutableList<LinhaDoTempoModel>,
    historico: ImmutableList<HistoricoDoDia>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TitleText(
                text = "Contas do Mês",
                size = 19.sp,
                fontWeight = FontWeight.W600
            )
        }

        val modifierGraficoDeBarras = Modifier.size(width = 360.dp, height = 295.dp)

        if (listContas.isNotEmpty()) { //OBS: NÃO TIRA O ISEMPTY DAQUI, SERVE PARA VERIFICAÇÃO DE LISTA VAZIA POR AQUI
            GraficoDeBarras(listContas, modifierGraficoDeBarras)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                "Linha do Tempo",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumnContas(historico.toImmutableList())

            //Fade do final da lista
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            )
        }
    }
}

@Composable
private fun LazyColumnContas(historicoContas: ImmutableList<HistoricoDoDia>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()

    ) {
        items(historicoContas) { dia ->
            //Paga o tamanho da lista
            val size = historicoContas.indexOf(dia)
            //Verifica se a conta é a última
            val isLastItem = size == historicoContas.lastIndex

            val linhaDoTempoPrincipal =
                LinhaDoTempoModel(
                    name = dia.primaryTimeline.name,
                    icon = dia.primaryTimeline.icon,
                    valor = dia.primaryTimeline.valor,
                    id = dia.primaryTimeline.id,
                    dateTime = dia.primaryTimeline.dateTime,
                    category = dia.primaryTimeline.category,
                    subCategory = dia.primaryTimeline.subCategory
                )

            HistoricoItem(
                linhaDoTempoPrincipal = linhaDoTempoPrincipal,
                lastIndex = isLastItem,
                linhaDoTempoOther = dia.otherTimeline
            )


        }
    }
}