package com.migueldk17.breeze.ui.features.historico.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.utils.formataSaldo
import java.time.LocalDate
import android.content.ContentValues.TAG
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity

import com.migueldk17.breeze.ui.theme.Blue

@Composable
fun HistoricoItem(
    date: LocalDate, //Data de crição da conta
    nameAccountFirst: String, //Nome da conta principal
    breezeIconFirst: BreezeIconsType, //Icone da conta principal
    princeFirst: Double, //Valor da conta principal
    lastIndex: Boolean, //Booleano que indica se a conta é a última da lista ou não
    contas: List<Conta> //Lista de outras contas que ficam escondidas sob o estado
){
    //Controla a expanção/contração das outras contas
    val expanded = remember{ mutableStateOf(false) }
    val density = LocalDensity.current


    Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        ) {
            Canvas(
                modifier = Modifier
                    .width(2.dp)
                    .matchParentSize()
                    .align(Alignment.CenterStart)
                    .padding(start = 69.dp)
            ) {
                val startY = with(density) { 15.dp.toPx()}
                val endOffset = with(density) { 5.dp.toPx() }

                val finalHeight = if (lastIndex) {
                    0f
                } else {
                    size.height + endOffset
                }
                //Linha da linha do tempo que inicia abaixo da BoxDate
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, startY),
                    end = Offset(0f, finalHeight ),
                    strokeWidth = 4f
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Conta principal, a última adicionada em um mesmo dia
                ContaPrincipal(date, nameAccountFirst, breezeIconFirst, princeFirst)

                if (contas.isNotEmpty()) {
                    VerMaisButton(contas.size, expanded)

                    //Adiciona uma animação ao expandir a lista
                    AnimatedVisibility(
                        visible = expanded.value,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        //Conta Secundária, caso haja mais de uma outra conta no mesmo dia as contas mais antigas são mandadas pra cá
                        ContaSecundaria(contas, expanded)
                    }
                }
                else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {  }
                }


            }

        }
    }
