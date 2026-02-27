package com.migueldk17.breeze.ui.features.historico.ui.layouts

import android.util.Log
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.MainActivity
import com.migueldk17.breeze.R
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.features.historico.ui.components.HistoricoDoMesReceitaBody
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoReceitaViewModel
import com.migueldk17.breeze.uistate.UiState
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HistoricoDoMesReceita(
    viewModelReceita: HistoricoReceitaViewModel,
    modifier: Modifier = Modifier,
) {
    val receitaState = viewModelReceita.receitaState.collectAsStateWithLifecycle().value

    val dataFormatada = viewModelReceita.data.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val activity = LocalActivity.current

    val observeReceitasMes = dataFormatada.isNotEmpty()


    LaunchedEffect(observeReceitasMes) {
            viewModelReceita.observarReceitasPorMes()
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
            HistoricoDoMesReceitaBody(
                modifier = modifier,
                receita = receitas.toImmutableList(),
                viewModelReceita = viewModelReceita
            )
        }
    }
}

