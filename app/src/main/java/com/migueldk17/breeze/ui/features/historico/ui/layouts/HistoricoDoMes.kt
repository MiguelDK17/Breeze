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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoDoMes(modifier: Modifier,viewModel: HistoricoDoMesViewModel){
    val contas = viewModel.getContasDoMes().collectAsStateWithLifecycle(initialValue = emptyList()).value

        Column(
            modifier = modifier
        ) {
            val modifier = Modifier.size(width = 360.dp, height = 295.dp)
            GraficoDeBarras(contas, modifier)

        }
}
