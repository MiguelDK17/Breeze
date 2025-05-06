package com.migueldk17.breeze.ui.features.historico.ui.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

import kotlin.math.max

@Composable
fun BarraAnimada(
    valor: Float,
    maxValue: Float,
    cor: Brush,
    dia: Int,
    delayAnimacao: Int,
    formatado: String
){
    val alturaAnimada = remember { Animatable(0f) }
    val density = LocalDensity.current

    val alturaFinal = (valor / maxValue) * 230f

    LaunchedEffect(Unit) {
        delay(delayAnimacao.toLong())
        alturaAnimada.animateTo(
            targetValue = alturaFinal,
            animationSpec = tween(
                durationMillis = 600,
                easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                }
            )
        )
    }

    val alturaEmDp = with(density) { alturaAnimada.value.toDp()}
    val offsetY = alturaEmDp + if (formatado.length > 6) 10.dp else 0.dp

    Column(
        modifier = Modifier.wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(200.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = formatado,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.offset(y = -offsetY),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Canvas(
                modifier = Modifier
                    .width(40.dp)
                    .height(150.dp)
            ) {
                drawRoundRect(
                    brush = cor,
                    topLeft = Offset(0f, size.height - alturaAnimada.value),
                    size = Size(size.width, alturaAnimada.value),
                    cornerRadius = CornerRadius(x = 20f, y = 20f)
                )
            }
        }
        Text(
            text = "$dia",
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp)
        )
    }
}