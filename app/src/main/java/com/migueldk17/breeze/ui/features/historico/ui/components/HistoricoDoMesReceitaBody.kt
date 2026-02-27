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
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoReceitaViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.text.ifEmpty

@Composable
fun HistoricoDoMesReceitaBody(
    receita: ImmutableList<LinhaDoTempoModel>,
    viewModelReceita: HistoricoReceitaViewModel,
    modifier: Modifier = Modifier,
    )
{
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
                text = "Receitas do Mês",
                size = 19.sp,
                fontWeight = FontWeight.W600
            )
        }

        GraficoDeBarras(
            graficoDoDiaModel = receita,
            modifier = Modifier
                .size(width = 360.dp, height = 295.dp)
        )

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
            val historicoReceitas = viewModelReceita.organizaReceitas(receita)

            LazyColumnReceitas(historicoReceitas = historicoReceitas.toImmutableList())

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
private fun LazyColumnReceitas(historicoReceitas: ImmutableList<HistoricoDoDia>){
    LazyColumn {
        items(historicoReceitas) { dia ->
            val size = historicoReceitas.indexOf(dia)
            val isLastItem = size == historicoReceitas.lastIndex
            val referenceBreezeIcon = dia.primaryTimeline.icon
            val name = dia.primaryTimeline.name.ifEmpty { "Receita" }
            val breezeIcon = if (referenceBreezeIcon.isEmpty()) BreezeIcons.Linear.Money.DollarCircle else BreezeIcons.Linear.Money.DollarCircle
            val linhaDoTempoPrincipal = LinhaDoTempoModel(
                name = name,
                icon = breezeIcon.enum.name,
                valor = dia.primaryTimeline.valor,
                id = dia.primaryTimeline.id,
                dateTime = dia.primaryTimeline.dateTime,
                isReceita = true
            )

            HistoricoItem(
                linhaDoTempoPrincipal = linhaDoTempoPrincipal,
                lastIndex = isLastItem,
                linhaDoTempoOther = dia.otherTimeline
            )
        }
    }
}