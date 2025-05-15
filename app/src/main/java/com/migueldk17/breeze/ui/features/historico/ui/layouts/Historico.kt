package com.migueldk17.breeze.ui.features.historico.ui.layouts


import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.MainActivity4
import com.migueldk17.breeze.ui.features.historico.ui.components.Calendario
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoViewModel


@Composable
fun Historico(viewModel: HistoricoViewModel = hiltViewModel()){
    //Contas encontradas do mes
    val contasEncontradas = viewModel.contasEncontradas.collectAsStateWithLifecycle().value
    //Data já traduzida pro português
    val dataTraduzida = viewModel.dataTraduzida.collectAsStateWithLifecycle().value
    when (contasEncontradas){
        null -> Log.d(TAG, "Historico: deu null")
        true -> {
            val context = LocalContext.current
            val intent = Intent(context, MainActivity4::class.java)
            intent.putExtra("data", dataTraduzida)
            context.startActivity(intent)
        }
        false -> {
            Calendario(viewModel)
            Log.d(TAG, "Historico: $contasEncontradas")

        }
        }
    }
