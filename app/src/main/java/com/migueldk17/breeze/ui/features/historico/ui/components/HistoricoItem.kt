package com.migueldk17.breeze.ui.features.historico.ui.components


import android.annotation.SuppressLint
import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.entity.Conta
import java.time.LocalDate
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity

@SuppressLint("UnusedBoxWithConstraintsScope")
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



    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ) {
        val horizontalValue = if (maxWidth < 380.dp) 15f else 15f //Padding responsivo de acordo com a largura da tela
        val isSmallScreen = maxWidth < 380.dp //Boolean que controla se a tela é pequena ou não

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
                    15f
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
