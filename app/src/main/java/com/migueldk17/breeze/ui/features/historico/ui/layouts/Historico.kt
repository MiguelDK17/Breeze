package com.migueldk17.breeze.ui.features.historico.ui.layouts


import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.MainActivity4
import com.migueldk17.breeze.ui.features.historico.ui.components.Calendario
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoViewModel
import com.migueldk17.breeze.uistate.UiState


@Composable
fun Historico(viewModel: HistoricoViewModel = hiltViewModel()){
    //Contas encontradas do mes
    val contasEncontradas = viewModel.buscaContasPorMes().collectAsStateWithLifecycle(initialValue = UiState.Loading).value

    //Data já traduzida pro português
    val dataTraduzida = viewModel.dataTraduzida.collectAsStateWithLifecycle().value
    when (contasEncontradas){
        is UiState.Empty -> {
            Toast.makeText(LocalContext.current, "Não há contas registradas neste mês", Toast.LENGTH_SHORT).show()
        }
        is UiState.Error -> {
            Toast.makeText(LocalContext.current, "Ocorreu um erro ao buscar contas", Toast.LENGTH_SHORT).show()
            UiState.Error(contasEncontradas.exception)
        }
        is UiState.Loading -> {

        }
        is UiState.Success -> {
            UiState.Success(contasEncontradas.data)
            val context = LocalContext.current
            val intent = Intent(context, MainActivity4::class.java)
            intent.putExtra("data", dataTraduzida)
            context.startActivity(intent)
        }
    }
}
