package com.migueldk17.breeze.ui.features.historico.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.converters.toColor
import com.migueldk17.breeze.ui.features.historico.model.GraficoDoDiaModel
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.utils.formataSaldo

@Composable
fun GraficoDeBarras(
    graficoDoDiaModel: List<LinhaDoTempoModel>,
    modifier: Modifier = Modifier
){
    //Pega a densidade da tela
    val density = LocalDensity.current
    //Adiciona um deslocamento baseado na densidade da tela em pixels
    val deslocamento = with(density) { 35.dp.toPx()}
    //Adiciona um deslocamento baseado na densidade da tela em pixels
    val larguraPx = with(density) { 290.dp.toPx()}

    OutlinedCard(
        modifier = Modifier
            .width(360.dp)
            .height(295.dp)
            .padding(vertical = 10.dp)
    ) {

        Column(
            modifier = modifier
                .background(if (!isSystemInDarkTheme()) Color.White else Color.Transparent)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.height(240.dp)
            ) {
                // Texto lateral
                Column(
                    modifier = Modifier
                        .width(60.dp)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Total gasto", fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(110.dp))
                    Text("Dias do mês", fontSize = 12.sp)
                }

                // Linha vertical e base
                Canvas(
                    modifier = Modifier
                        .height(300.dp)
                ) {
                    val endY = size.height
                    val linhaFinal = endY - deslocamento //Calcula a posição da linha com base na densidade de pixels calculada acima e o height da Canvas para que fique responsivo
                    //Linha de cima
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, 50f),
                        end = Offset(0f, linhaFinal)
                    )
                    //Linha de baixo
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, linhaFinal),
                        end = Offset(larguraPx, linhaFinal)
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(graficoDoDiaModel) { index, grafico ->
                        val alturaMaxima = graficoDoDiaModel.maxOfOrNull { it.valor.toFloat() } ?: 1f
                        val texto = formataSaldo(grafico.valor)
                        val listColors = listOf(grafico.colorIcon.toColor(), grafico.colorCard.toColor())
                        val brush = Brush.verticalGradient(colors = listColors)
                        val dayOfMonth = grafico.dateTime.dayOfMonth
                        BarraAnimada(
                            valor = grafico.valor.toFloat(),
                            maxValue = alturaMaxima,
                            cor = brush,
                            dia = dayOfMonth,
                            delayAnimacao = index * 100, //delay em milissegundos
                            valorFormatado = texto)

                    }
                }

            }
        }
    }
}
