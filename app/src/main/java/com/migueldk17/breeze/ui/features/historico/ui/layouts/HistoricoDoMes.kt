package com.migueldk17.breeze.ui.features.historico.ui.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoDoMes(viewModel: HistoricoViewModel){
    val data = viewModel.dataTraduzida.collectAsStateWithLifecycle()
    val contas = viewModel.contasFiltradas.collectAsStateWithLifecycle()
        Column {
            contas.value.forEach { conta ->
                val dia = conta.dateTime.toLocalDateTime().dayOfMonth
                val valor = conta.valor.toFloat()
                val list : List<Pair<Int, Float>> = listOf(Pair(10, 120.00f), Pair(12, 200.00f), Pair(15, 80.00f), Pair(18, 220.00f), Pair(25, 500.00f))
                val contaPair = listOf(Pair(dia, valor))
                val modifier = Modifier.size(width = 360.dp, height = 295.dp)
                GraficoDeBarras(contaPair, modifier)
            }
        }
}
