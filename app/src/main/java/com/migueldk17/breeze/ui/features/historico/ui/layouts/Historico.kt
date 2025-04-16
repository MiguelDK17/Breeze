package com.migueldk17.breeze.ui.features.historico.ui.layouts


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.migueldk17.breeze.ui.features.historico.ui.components.Calendario
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoViewModel


@Composable
fun Historico(viewModel: HistoricoViewModel = hiltViewModel()){

    Calendario(viewModel)
}