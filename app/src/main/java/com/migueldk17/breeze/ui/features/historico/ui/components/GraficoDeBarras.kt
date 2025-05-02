package com.migueldk17.breeze.ui.features.historico.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.converters.toColor
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.utils.formataSaldo

@Composable
fun GraficoDeBarras(
    contas: List<Conta>,
    modifier: Modifier = Modifier
){
    //Adiciona um valor máximo que a barra pode chegar
    val maxValue = contas.maxOfOrNull { it.valor.toFloat() } ?: 1f
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
                .background(Color.White)
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
                        .background(Color.Red)
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
                    items(contas) { (_, _, value,_,colorIcon,colorCard,dateTime) ->

                        val animatedHeight by animateFloatAsState(
                            targetValue = (value.toFloat() / maxValue) * 230f,
                            label = "BarAnimation"
                        )

                        val day = dateTime.toLocalDateTime().dayOfMonth

                        val colorCard = colorCard.toColor()
                        val colorIcon = colorIcon.toColor()

                        val brush = Brush.verticalGradient(
                            colors = listOf(colorIcon, colorCard)
                        )
                        Column(
                            modifier = Modifier.wrapContentHeight()

                        ) {
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(200.dp),
                                contentAlignment = Alignment.BottomCenter

                            ) {
                                //Pega o valor passado pra função e manda para formataSaldo, para que retorne o valor em moeda
                                val texto = formataSaldo(value.toDouble())

                                //Caso o tamanho do texto for maior que 7 adiciona um pouco a mais de espaço para que o Text não fique dentro das barras
                                val tamanhoExtra = if (texto.length > 6) 10f else 0f
                                Log.d(TAG, "GraficoDeBarras: tamanhoExtra: $tamanhoExtra")

                                // Divide o valor da barra pela metade e adiciona um extra para que o texto fique um pouco acima
                                val tamanhoPosicaoTexto = animatedHeight / 2 + 20 + tamanhoExtra

                                Log.d(TAG, "GraficoDeBarras: tamanhoPosicaoTextp: $tamanhoPosicaoTexto")

                                val tamanhoEmPx = with(density) { animatedHeight.toDp() }

                                val offsetY = tamanhoEmPx + tamanhoExtra.dp

                                Text(
                                    texto,
                                    style = TextStyle(fontSize = 12.sp),
                                    modifier = Modifier
                                        .offset( y = (-offsetY)) //Sobe o texto
                                        ,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Canvas(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(150.dp)
                                ) {
                                    //Retangulos do gráfico
                                    drawRoundRect(
                                        brush = brush,
                                        topLeft = Offset(0f, size.height - animatedHeight),
                                        size = Size(size.width, animatedHeight),
                                        cornerRadius = CornerRadius(x = 20f, y = 20f)
                                    )
                                }


                            }
                            Text(
                                text = "$day",
                                style = TextStyle(fontSize = 12.sp),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 10.dp)
                            )

                        }

                    }
                }

            }
        }
    }
}
