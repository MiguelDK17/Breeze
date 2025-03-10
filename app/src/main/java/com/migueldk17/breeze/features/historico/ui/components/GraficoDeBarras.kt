package com.migueldk17.breeze.features.historico.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.theme.Blue
import com.migueldk17.breeze.ui.theme.PastelLightBlue

@Composable
fun GraficoDeBarras(
    data: List<Pair<Int, Float>>,
    modifier: Modifier = Modifier
){
    val maxValue = data.maxOfOrNull { it.second } ?: 1f
    val barWidth = 40.dp // Largura das barras
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Total gasto", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(180.dp))
                Text("Dias do mês", textAlign = TextAlign.Center)
            }
            //Canvas das linhas
            Canvas(
                modifier = Modifier.height(100.dp).width(20.dp)
            ) {
                //Linha de cima
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 50f),
                    end = Offset(0f, 430f)
                )
                //Linha de baixo
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 430f),
                    end = Offset(455f, 430f)
                )
            }
            data.forEach { (day, value) ->
                val animatedHeight by animateFloatAsState(
                    targetValue = (value / maxValue) * 200f,
                    label = "BarAnimation"
                )
                Box(modifier = Modifier
                    .size(width = barWidth, height = 200.dp),
                    contentAlignment = Alignment.BottomCenter

                ) {
                    Text("R$ ${value.toInt()}", style = TextStyle(fontSize = 12.sp))
                    Spacer(modifier = Modifier.size(100.dp).background(Color.Yellow))
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            //Retangulos do gráfico
                            drawRect(
                                color = Color.Gray,
                                topLeft = Offset(0f, size.height - animatedHeight),
                                size = Size(size.width, animatedHeight)
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "$day", style = TextStyle(fontSize = 12.sp))
                    }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    val list : List<Pair<Int, Float>> = listOf(Pair(10, 65f), Pair(12, 103f), Pair(15, 55f), Pair(18, 134f))
    val modifier = Modifier.size(width = 360.dp, height = 295.dp)

    GraficoDeBarras(list, modifier)
}