package com.migueldk17.breeze.ui.features.historico.ui.components

import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.migueldk17.breeze.ui.formataSaldo
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
            .background(Color.White)
            .padding(8.dp)
    ) {
        Text(
            "Total gasto",
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

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
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Total gasto", fontSize = 12.sp)
                Spacer(modifier = Modifier.height(150.dp))
                Text("Dias do mês", fontSize = 12.sp)
            }

            // Linha vertical e base
            Canvas(
                modifier = Modifier
                    .height(200.dp)
                    .background(Color.Gray)
            ) {
                val startY = 0f
                val endY = size.height
                //Linha de cima
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(0f, endY)
                )
                //Linha de baixo
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, endY),
                    end = Offset(455f, endY)
                )
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(data) { (day, value) ->
                    val animatedHeight by animateFloatAsState(
                        targetValue = (value / maxValue) * 200f,
                        label = "BarAnimation"
                    )
                    Column(
                        modifier = Modifier.fillMaxSize()

                    ) {
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(200.dp),
                            contentAlignment = Alignment.BottomCenter

                        ) {
                            // Divide o valor da barra pela metade e adiciona um extra para que o texto fique um pouco acima
                            val tamanhoPosicaoTexto = animatedHeight / 2 + 20
                            Log.d(TAG, "GraficoDeBarras: tamanho em float: $tamanhoPosicaoTexto")
                            Log.d(TAG, "GraficoDeBarras: tamanho em dp: ${animatedHeight.dp}")
                            Text(
                                formataSaldo(value.toDouble()),
                                style = TextStyle(fontSize = 12.sp),
                                modifier = Modifier
                                    .size(tamanhoPosicaoTexto.dp),
                                textAlign = TextAlign.Center
                            )
                            Canvas(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(150.dp)
                            ) {
                                //Retangulos do gráfico
                                drawRect(
                                    color = Color.Gray,
                                    topLeft = Offset(0f, size.height - animatedHeight),
                                    size = Size(size.width, animatedHeight)
                                )
                            }


                        }
                        Text(text = "$day",
                            style = TextStyle(fontSize = 12.sp),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 10.dp))

                    }

                }
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    //Lista contendo dia e preco da conta(direita o dia, e esquerda o valor da conta)
    val list : List<Pair<Int, Float>> = listOf(Pair(10, 120.00f), Pair(12, 200.00f), Pair(15, 80.00f), Pair(18, 220.00f))
    val modifier = Modifier.size(width = 360.dp, height = 295.dp)

    GraficoDeBarras(list, modifier)
}