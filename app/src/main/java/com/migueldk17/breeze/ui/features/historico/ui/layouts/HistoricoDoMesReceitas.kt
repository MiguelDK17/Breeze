package com.migueldk17.breeze.ui.features.historico.ui.layouts

import android.util.Log
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.MainActivity
import com.migueldk17.breeze.R
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.features.historico.ui.components.HistoricoItem
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoReceitaViewModel
import com.migueldk17.breeze.uistate.UiState
import kotlin.text.ifEmpty

@Composable
fun HistoricoDoMesReceita(
    modifier: Modifier,
    viewModelReceita: HistoricoReceitaViewModel,
) {
    val receitaState = viewModelReceita.receitaState.collectAsStateWithLifecycle().value

    val dataFormatada = viewModelReceita.data.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val activity = LocalActivity.current

    LaunchedEffect(Unit) {
        if (dataFormatada.isNotEmpty()) {
            viewModelReceita.observarReceitasPorMes()
        }
    }
    when (receitaState) {
        is UiState.Loading -> {
            AnimatedVisibility(
                visible = true,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                LottieAnimation(animationRes = R.raw.loading_breeze, isPlaying = true)
            }

        }
        is UiState.Empty -> ListaVaziaHistorico(
            animationRes = R.raw.piggy_saving_money,
            titleText = "Sem Receitas por Aqui!",
            descriptionText1 = "Ainda não há nenhuma receita cadastrada neste mês!",
            descriptionText2 = "Você pode começar adicionando uma na Página Inicial 💰!",
            buttonText = "Adicionar Receita",
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("showBottomSheet", true)
                context.startActivity(intent)
                activity?.finish()
            }
        )
        is UiState.Error -> {
            val error = (receitaState.exception)
            Log.d(TAG, "HistoricoDoMesReceita: Erro desconhecido detectado: $error")
        }
        is UiState.Success -> {
            val receitas = receitaState.data
            ColumnReceitas(
                modifier = modifier,
                receita = receitas,
                viewModelReceita = viewModelReceita
            )
        }
    }
}

@Composable
private fun ColumnReceitas(
    modifier: Modifier,
    receita: List<LinhaDoTempoModel>,
    viewModelReceita: HistoricoReceitaViewModel
){
    Column(
        modifier = modifier
    ) {
        val modifier = Modifier.size(width = 360.dp, height = 295.dp)

        GraficoDeBarras(receita, modifier)

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

            LazyColumnReceitas(
                historicoReceitas = historicoReceitas
            )

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
private fun LazyColumnReceitas(historicoReceitas: List<HistoricoDoDia>){
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