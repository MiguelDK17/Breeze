package com.migueldk17.breeze.ui.features.historico.ui.comparativo

import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.GastoCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.SaldoDoMesCard
import com.migueldk17.breeze.ui.theme.RedError
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.R
import com.migueldk17.breeze.domain.model.BreezeInsight
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.ComparativoModel
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoComparativoViewModel
import com.migueldk17.breeze.uistate.UiState

@Composable
fun HistoricoDoMesComparativo(
    modifier: Modifier = Modifier,
    viewModel: HistoricoComparativoViewModel = hiltViewModel()
){
    val comparativoModel = viewModel.comparativoModel.collectAsStateWithLifecycle().value
    val listaDeMovimentacoesMensal = comparativoModel.listaDeMovimentacoesMensal
    val listaDeMovimentacoesDiaria = comparativoModel.listaDeMovimentacoesDiaria
    val listaDeMovimentacoesCategoria = comparativoModel.listaDeMovimentacoesCategoria

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    val listaSelecionada = when (comparativoModel.tipoDeDados) {
        TipoDeDados.MES -> listaDeMovimentacoesMensal
        TipoDeDados.DIA -> listaDeMovimentacoesDiaria
        TipoDeDados.CATEGORIA -> listaDeMovimentacoesCategoria
    }

    Log.d(TAG, "HistoricoDoMesComparativo:listaSelecionada: $listaSelecionada")
    val mesBackup = viewModel.mes.collectAsStateWithLifecycle().value
    val insight by viewModel.insightDoMes.collectAsStateWithLifecycle()

    AnimatedContent(
        targetState = state,
        transitionSpec = {
            fadeIn(tween(300)) + scaleIn(initialScale = 0.95f) togetherWith fadeOut(tween(200))
        },
        label = "transicao_de_estados"
    ) { state ->
        when (state){
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Empty -> {
                EmptyStateLayout(
                    voltarParaMes = {
                        viewModel.voltarParaMes()
                    }
                )
            }
            is UiState.Success -> {
                val data = state.data
                HistoricoDoMesComparativoBody(
                    data = data,
                    comparativoModel = comparativoModel,
                    mesBackup = mesBackup,
                    insight = insight,
                    setDia = { viewModel.setDia(it) },
                    setCategoria = { viewModel.setCategoria() },
                    voltarParaMes = { viewModel.voltarParaMes() },
                    modifier = modifier
                )
            }
            is UiState.Error -> {
                val error = state.exception
                Log.d(TAG, "HistoricoDoMesComparativo: erro detectado: $error")
            }
        }
    }


}

@Composable
private fun EmptyStateLayout(
    voltarParaMes: () -> Unit,
    modifier: Modifier = Modifier,

){
    val backgroundColor = Color(0xFFD8E6EE)
    val iconSize = 120.dp
    val halfIconSize = iconSize / 2

    val infiniteTransition = rememberInfiniteTransition(label = "breeze_float")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breeze_float_y"
    )

    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = R.drawable.breeze_empty_state),
            contentDescription = "Nenhum gasto hoje",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .offset(y = offsetY.dp),
            contentScale = ContentScale.Fit
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFFEBF4F8)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            top = halfIconSize,
                            start = 12.dp,
                            end = 12.dp,
                            bottom = 24.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BreezeRegularText(
                        text = "Nenhum gasto hoje!",
                        fontWeight = FontWeight.SemiBold,
                        size = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    DescriptionText(
                        "Dia tranquilo...",
                        color = Color(0xFF798FA0)
                    )
                    Spacer(modifier = Modifier
                        .height(20.dp))

                    BreezeButton(
                        onClick = {
                            voltarParaMes()
                        },
                        text = "Tudo bem!"
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.calendario_check_removebg_preview),
                contentDescription = "Calendário",
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.TopCenter)
                    .offset(y = -halfIconSize)
            )
        }



    }
}



@Composable
private fun HistoricoDoMesComparativoBody(
    data: ComparativoData,
    comparativoModel: ComparativoModel,
    mesBackup: String,
    insight: BreezeInsight?,
    modifier: Modifier = Modifier,
    setDia: (String) -> Unit = {},
    setCategoria: () -> Unit = {},
    voltarParaMes : () -> Unit = {},
    ){
    val scroll = rememberScrollState()


    Column(
        modifier = modifier
            .widthIn(min = 389.dp)
            .heightIn(min = 464.dp)
            .padding(7.dp)
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .background(color = RedError)
                .height(10.dp)
        )

        SaldoDoMesCard(
            data = data,
            comparativoModel = comparativoModel,
            mesBackup = mesBackup,
            setCategoria = setCategoria,
            setDia = setDia,
            converteDiaEmMes = voltarParaMes
        )
        insight?.let { dica ->
            GastoCard(
                titulo = dica.titulo,
                subTitulo = dica.subTitulo
            )
        }


    }
}