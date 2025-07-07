package com.migueldk17.breeze.ui.features.historico.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoMesReceita

@Composable
fun HistoricoDoMesReceita(
    modifier: Modifier,
    viewModel: HistoricoMesReceita
){
    val receitas = viewModel.receitasPorMes.collectAsStateWithLifecycle().value
    val dataFormatada = viewModel.data.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        Log.d(TAG, "HistoricoDoMesReceita: $dataFormatada")
    }

    Column(
        modifier = modifier
    ) {


    }

}