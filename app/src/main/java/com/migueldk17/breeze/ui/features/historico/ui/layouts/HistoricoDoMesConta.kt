package com.migueldk17.breeze.ui.features.historico.ui.layouts

import android.content.Intent
import android.util.Log
import android.content.ContentValues.TAG
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.migueldk17.breeze.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.MainActivity3
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.features.historico.ui.components.HistoricoDoMesContaBody
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.uistate.UiState
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoDoMesConta(
    viewModelContas: HistoricoDoMesViewModel,
    modifier: Modifier = Modifier,
    ) {
    val contasState = viewModelContas.contasPorMesState.collectAsStateWithLifecycle().value

    //Pega as contas já filtradas por data da mais recente a mais antiga
    val historico = viewModelContas.historico.collectAsStateWithLifecycle().value

    val dataFormatada = viewModelContas.data.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val activity = LocalActivity.current

    val observeContasMes = dataFormatada.isNotEmpty()

    LaunchedEffect(observeContasMes) {
        viewModelContas.observarContaPorMes()
    }

    when(contasState) {
        is UiState.Loading -> {
            AnimatedVisibility(
                visible = true,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                LottieAnimation(animationRes = R.raw.loading_breeze, isPlaying = true)
            }

        }
        is UiState.Empty -> {
            ListaVaziaHistorico(
                animationRes = R.raw.empty_ghost,
                titleText = "Nenhuma conta por aqui... 👻",
                descriptionText1 = "Parece que suas contas ainda estão no mundo dos fantasmas.",
                descriptionText2 = "Crie uma pra começar a organizar tudo certinho!",
                buttonText = "Criar Conta",
                onClick = {
                    val intent = Intent(context, MainActivity3::class.java)
                    context.startActivity(intent)
                    activity?.finish()
                }
            )
        }
        is UiState.Error -> {
            val error = (contasState.exception)
            Log.d(TAG, "HistoricoDoMesConta: $error")
        }
        is UiState.Success -> {
            val contas = (contasState.data)
            HistoricoDoMesContaBody(
                listContas = contas.toImmutableList(),
                historico = historico.toImmutableList(),
                modifier = modifier
            )
        }
        else -> {
            Log.d(TAG, "HistoricoDoMesConta: carregando")
        }
    }
    }

