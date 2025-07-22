package com.migueldk17.breeze.ui.features.historico.ui.layouts


import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.MainActivity4
import com.migueldk17.breeze.ui.features.historico.ui.components.Calendario
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoViewModel
import com.migueldk17.breeze.uistate.UiState


@Composable
fun Historico(viewModel: HistoricoViewModel = hiltViewModel()){
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.navegarParaTela.collect { (mes, dataFormatada) ->
            val context = context
            val intent = Intent(context, MainActivity4::class.java)
            intent.putExtra("mes", mes)
            intent.putExtra("dataFormatada", dataFormatada)
            context.startActivity(intent)

        }
    }

    Calendario(viewModel)

}
